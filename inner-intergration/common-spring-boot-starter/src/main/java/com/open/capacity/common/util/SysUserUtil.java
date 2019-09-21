package com.open.capacity.common.util;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.open.capacity.common.auth.details.LoginAppUser;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年11月12日 上午22:57:51 获取用户信息
 */
public class SysUserUtil {

	/**
	 * 获取登陆的 LoginAppUser
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static LoginAppUser getLoginAppUser() {
		
		//当内部服务，不带token时，内部服务
		String accessToken =  TokenUtil.getToken() ;
		RedisTemplate redisTemplate =  SpringUtils.getBean(RedisTemplate.class);
		Map<String, Object> params = (Map<String, Object>) redisTemplate.opsForValue().get("token:" + accessToken);
		if(params!=null){
			return (LoginAppUser) params.get("auth") ;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
			authentication = oAuth2Auth.getUserAuthentication();

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
				return (LoginAppUser) authenticationToken.getPrincipal();
			} else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
				// 刷新token方式
				PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
				return (LoginAppUser) authenticationToken.getPrincipal();

			}
		}
		
		
		

		return null;
	}
}
