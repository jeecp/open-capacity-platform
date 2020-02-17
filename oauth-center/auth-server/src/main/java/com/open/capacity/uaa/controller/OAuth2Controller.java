package com.open.capacity.uaa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.open.capacity.common.auth.details.LoginAppUser;
import com.open.capacity.common.model.SysPermission;
import com.open.capacity.common.token.SmsCodeAuthenticationToken;
import com.open.capacity.common.util.SysUserUtil;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.log.annotation.LogAnnotation;
import com.open.capacity.uaa.server.service.RedisClientDetailsService;
import com.open.capacity.uaa.utils.SpringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2018年4月28日 下午2:18:54 类说明
 */
@Slf4j
@RestController
@Api(tags = "OAuth API")
public class OAuth2Controller {

	@Resource
	private ObjectMapper objectMapper; // springmvc启动时自动装配json处理类
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ApiOperation(value = "用户名密码获取token")
	@PostMapping("/oauth/user/token")
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public void getUserTokenInfo(
			@ApiParam(required = true, name = "username", value = "账号") @RequestParam(value = "username") String username,
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		String clientId = request.getHeader("client_id");
		String clientSecret = request.getHeader("client_secret");

		try {

			if (clientId == null || "".equals(clientId)) {
				throw new UnapprovedClientAuthenticationException("请求头中无client_id信息");
			}

			if (clientSecret == null || "".equals(clientSecret)) {
				throw new UnapprovedClientAuthenticationException("请求头中无client_secret信息");
			}

			RedisClientDetailsService clientDetailsService = SpringUtil.getBean(RedisClientDetailsService.class);

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

			if (clientDetails == null) {
				throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
			} else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
				throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
			}

			TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(),
					"customer");

			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

			AuthenticationManager authenticationManager = SpringUtil.getBean(AuthenticationManager.class);

			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

			AuthorizationServerTokenServices authorizationServerTokenServices = SpringUtil
					.getBean("defaultAuthorizationServerTokenServices", AuthorizationServerTokenServices.class);

			OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices
					.createAccessToken(oAuth2Authentication);

			oAuth2Authentication.setAuthenticated(true);

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {

			response.setStatus(HttpStatus.UNAUTHORIZED.value());

			response.setContentType("application/json;charset=UTF-8");

			Map<String, String> rsp = new HashMap<>();
			rsp.put("resp_code", HttpStatus.UNAUTHORIZED.value() + "");
			rsp.put("resp_msg", e.getMessage());

			try {
				response.getWriter().write(objectMapper.writeValueAsString(rsp));
				response.getWriter().flush();
				response.getWriter().close();
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				log.error("OAuth2Controller -> getUserTokenInfo1 : {}" + e1.getMessage());
			} catch (IOException e1) {
				log.error("OAuth2Controller -> getUserTokenInfo2 : {}" + e1.getMessage());
			}

		}
	}

	@ApiOperation(value = "clientId获取token")
	@PostMapping("/oauth/client/token")
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public void getClientTokenInfo(HttpServletRequest request, HttpServletResponse response) {

		String clientId = request.getHeader("client_id");
		String clientSecret = request.getHeader("client_secret");
		try {

			if (clientId == null || "".equals(clientId)) {
				throw new UnapprovedClientAuthenticationException("请求参数中无clientId信息");
			}

			if (clientSecret == null || "".equals(clientSecret)) {
				throw new UnapprovedClientAuthenticationException("请求参数中无clientSecret信息");
			}

			RedisClientDetailsService clientDetailsService = SpringUtil.getBean(RedisClientDetailsService.class);

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

			if (clientDetails == null) {
				throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
			} else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
				throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
			}

			Map<String, String> map = new HashMap<>();
			map.put("client_secret", clientSecret);
			map.put("client_id", clientId);
			map.put("grant_type", "client_credentials");
			TokenRequest tokenRequest = new TokenRequest(map, clientId, clientDetails.getScope(), "client_credentials");

			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

			AuthorizationServerTokenServices authorizationServerTokenServices = SpringUtil
					.getBean("defaultAuthorizationServerTokenServices", AuthorizationServerTokenServices.class);
			OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
			ClientCredentialsTokenGranter clientCredentialsTokenGranter = new ClientCredentialsTokenGranter(
					authorizationServerTokenServices, clientDetailsService, requestFactory);

			clientCredentialsTokenGranter.setAllowRefresh(true);
			OAuth2AccessToken oAuth2AccessToken = clientCredentialsTokenGranter.grant("client_credentials",
					tokenRequest);

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {

			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			Map<String, String> rsp = new HashMap<>();
			rsp.put("resp_code", HttpStatus.UNAUTHORIZED.value() + "");
			rsp.put("resp_msg", e.getMessage());

			try {
				response.getWriter().write(objectMapper.writeValueAsString(rsp));
				response.getWriter().flush();
				response.getWriter().close();
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				log.error("OAuth2Controller->getClientTokenInfo:{}" ,e1.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				log.error("OAuth2Controller->getClientTokenInfo:{}" ,e1.getMessage());
			}

		}
	}

	@ApiOperation(value = "access_token刷新token")
	@PostMapping(value = "/oauth/refresh/token", params = "access_token")
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public void refreshTokenInfo(String access_token, HttpServletRequest request, HttpServletResponse response) {

		// 拿到当前用户信息
		try (PrintWriter out = response.getWriter()) {

			try {
				Authentication user = SecurityContextHolder.getContext().getAuthentication();

				if (user != null) {
					if (user instanceof OAuth2Authentication) {
						Authentication athentication = (Authentication) user;
						OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) athentication.getDetails();
					}

				}
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);
				OAuth2Authentication auth = (OAuth2Authentication) user;
				RedisClientDetailsService clientDetailsService = SpringUtil.getBean(RedisClientDetailsService.class);

				if (auth != null) {
					ClientDetails clientDetails = clientDetailsService
							.loadClientByClientId(auth.getOAuth2Request().getClientId());

					AuthorizationServerTokenServices authorizationServerTokenServices = SpringUtil
							.getBean("defaultAuthorizationServerTokenServices", AuthorizationServerTokenServices.class);
					OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);

					RefreshTokenGranter refreshTokenGranter = new RefreshTokenGranter(authorizationServerTokenServices,
							clientDetailsService, requestFactory);

					Map<String, String> map = new HashMap<>();
					map.put("grant_type", "refresh_token");
					map.put("refresh_token", accessToken.getRefreshToken().getValue());
					TokenRequest tokenRequest = new TokenRequest(map, auth.getOAuth2Request().getClientId(),
							auth.getOAuth2Request().getScope(), "refresh_token");

					OAuth2AccessToken oAuth2AccessToken = refreshTokenGranter.grant("refresh_token", tokenRequest);

					tokenStore.removeAccessToken(accessToken);

					response.setContentType("application/json;charset=UTF-8");
					out.write(objectMapper.writeValueAsString(oAuth2AccessToken));
					out.flush();
					response.getWriter().close();
				}
			} catch (Exception e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType("application/json;charset=UTF-8");
				Map<String, String> rsp = new HashMap<>();
				rsp.put("resp_code", HttpStatus.UNAUTHORIZED.value() + "");
				rsp.put("resp_msg", e.getMessage());

				try {
					out.write(objectMapper.writeValueAsString(rsp));
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					log.error("OAuth2Controller->refreshTokenInfo1:{}" ,e1.getMessage());
				}
				out.flush();
			}

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			log.error("OAuth2Controller->refreshTokenInfo2:{}" ,e2.getMessage());
		}

	}

	/**
	 * 移除access_token和refresh_token
	 * 
	 * @param access_token
	 */
	@ApiOperation(value = "移除token")
	@PostMapping(value = "/oauth/remove/token", params = "access_token")
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public void removeToken(String access_token) {

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);
		if (accessToken != null) {
			// 移除access_token
			tokenStore.removeAccessToken(accessToken);

			// 移除refresh_token
			if (accessToken.getRefreshToken() != null) {
				tokenStore.removeRefreshToken(accessToken.getRefreshToken());
			}

		}
	}

	@ApiOperation(value = "获取token信息")
	@PostMapping(value = "/oauth/get/token", params = "access_token")
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public OAuth2AccessToken getTokenInfo(String access_token) {

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);

		return accessToken;

	}

	/**
	 * 当前登陆用户信息
	 * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()
	 * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication
	 * 
	 * @return
	 */
	@ApiOperation(value = "当前登陆用户信息")
	@RequestMapping(value = { "/oauth/userinfo" }, produces = "application/json") // 获取用户信息。/auth/user
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public Map<String, Object> getCurrentUserDetail() {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("user", SysUserUtil.getLoginAppUser());
		log.debug("认证详细信息:" + SysUserUtil.getLoginAppUser().toString());

		List<SysPermission> permissions = new ArrayList<>();

		new ArrayList(SysUserUtil.getLoginAppUser().getAuthorities()).forEach(o -> {
			SysPermission sysPermission = new SysPermission();
			sysPermission.setPermission(o.toString());
			permissions.add(sysPermission);
		});
		// userInfo.put("authorities",
		// AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
		// );
		userInfo.put("permissions", permissions);

		userInfo.put("resp_code", "200");

		log.info("返回信息:{}", userInfo);

		return userInfo;
	}

	@ApiOperation(value = "token列表")
	@PostMapping("/oauth/token/list")
	@LogAnnotation(module = "auth-server", recordRequestParam = false)
	public PageResult<HashMap<String, String>> getUserTokenInfo(@RequestParam Map<String, Object> params)
			throws Exception {
		List<HashMap<String, String>> list = new ArrayList<>();

		Set<String> keys = Optional.ofNullable(redisTemplate.keys("access:" + "*")).orElse(Sets.newHashSet(""));
		// Object key1 = keys.toArray()[0];
		// Object token1 = redisTemplate.opsForValue().get(key1);
		// 根据分页参数获取对应数据
		// List<String> pages = findKeysForPage("access:" + "*",
		// MapUtils.getInteger(params, "page"),MapUtils.getInteger(params,
		// "limit"));

		for (Object key : keys.toArray()) {
			// String key = page;
			// String accessToken = StringUtils.substringAfter(key, "access:");
			// OAuth2AccessToken token =
			// tokenStore.readAccessToken(accessToken);
			OAuth2AccessToken token = (OAuth2AccessToken) redisTemplate.opsForValue().get(key);
			HashMap<String, String> map = new HashMap<String, String>();

			try {
				
				if(token!=null){
					map.put("token_type", token.getTokenType());
					map.put("token_value", token.getValue());
					map.put("expires_in", token.getExpiresIn() + "");
				}
				OAuth2Authentication oAuth2Auth = tokenStore.readAuthentication(token);
				Authentication authentication = oAuth2Auth.getUserAuthentication();

				map.put("client_id", oAuth2Auth.getOAuth2Request().getClientId());
				map.put("grant_type", oAuth2Auth.getOAuth2Request().getGrantType());

				if (authentication instanceof UsernamePasswordAuthenticationToken) {
					UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

					if (authenticationToken.getPrincipal() instanceof LoginAppUser) {
						LoginAppUser user = (LoginAppUser) authenticationToken.getPrincipal();
						map.put("user_id", user.getId() + "");
						map.put("user_name", user.getUsername() + "");
						map.put("user_head_imgurl", user.getHeadImgUrl() + "");
					}

				} else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
					// 刷新token方式
					PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
					if (authenticationToken.getPrincipal() instanceof LoginAppUser) {
						LoginAppUser user = (LoginAppUser) authenticationToken.getPrincipal();
						map.put("user_id", user.getId() + "");
						map.put("user_name", user.getUsername() + "");
						map.put("user_head_imgurl", user.getHeadImgUrl() + "");
					}

				}
				list.add(map);
			} catch (Exception e) {

			}

			

		}

		return PageResult.<HashMap<String, String>>builder().data(list).code(0).count((long) keys.size()).build();

	}

	public List<String> findKeysForPage(String patternKey, int pageNum, int pageSize) {

		Set<String> execute = redisTemplate.execute(new RedisCallback<Set<String>>() {

			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

				Set<String> binaryKeys = new HashSet<>();

				Cursor<byte[]> cursor = connection
						.scan(new ScanOptions.ScanOptionsBuilder().match(patternKey).count(1000).build());
				int tmpIndex = 0;
				int startIndex = (pageNum - 1) * pageSize;
				int end = pageNum * pageSize;
				while (cursor.hasNext()) {
					if (tmpIndex >= startIndex && tmpIndex < end) {
						binaryKeys.add(new String(cursor.next()));
						tmpIndex++;
						continue;
					}

					// 获取到满足条件的数据后,就可以退出了
					if (tmpIndex >= end) {
						break;
					}

					tmpIndex++;
					cursor.next();
				}
				connection.close();
				return binaryKeys;
			}
		});

		List<String> result = new ArrayList<String>(pageSize);
		
		Optional.ofNullable(result).orElse(Lists.newArrayList("")).addAll(execute);
		return result;
	}

	@PostMapping("/authentication/sms")
	public void getMobileInfo(
			@ApiParam(required = true, name = "deviceId", value = "手机号") @RequestParam(value = "deviceId") String deviceId,
			@ApiParam(required = true, name = "validCode", value = "验证码") @RequestParam(value = "validCode", required = false) String validCode,
			HttpServletRequest request, HttpServletResponse response) {
		String clientId = request.getHeader("client_id");
		String clientSecret = request.getHeader("client_secret");

		try {
			if (clientId == null || "".equals(clientId)) {
				throw new UnapprovedClientAuthenticationException("请求头中无client_id信息");
			}

			if (clientSecret == null || "".equals(clientSecret)) {
				throw new UnapprovedClientAuthenticationException("请求头中无client_secret信息");
			}

			RedisClientDetailsService clientDetailsService = SpringUtil.getBean(RedisClientDetailsService.class);

			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

			if (clientDetails == null) {
				throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
			} else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
				throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
			}

			TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(),
					"customer");

			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

			SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(deviceId);
			// UsernamePasswordAuthenticationToken token = new
			// UsernamePasswordAuthenticationToken(username, password);

			AuthenticationManager authenticationManager = SpringUtil.getBean(AuthenticationManager.class);

			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

			AuthorizationServerTokenServices authorizationServerTokenServices = SpringUtil
					.getBean("defaultAuthorizationServerTokenServices", AuthorizationServerTokenServices.class);

			OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices
					.createAccessToken(oAuth2Authentication);

			oAuth2Authentication.setAuthenticated(true);

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {

			response.setStatus(HttpStatus.UNAUTHORIZED.value());

			response.setContentType("application/json;charset=UTF-8");

			Map<String, String> rsp = new HashMap<>();
			rsp.put("resp_code", HttpStatus.UNAUTHORIZED.value() + "");
			rsp.put("resp_msg", e.getMessage());

			try {
				response.getWriter().write(objectMapper.writeValueAsString(rsp));
				response.getWriter().flush();
				response.getWriter().close();
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				log.error("OAuth2Controller->getMobileInfo:{}" ,e1.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				log.error("OAuth2Controller->getMobileInfo:{}" ,e1.getMessage());
			}

		}
	}
	  
}
