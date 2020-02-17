package com.open.capacity.uaa.filter;

import com.open.capacity.uaa.server.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component("smsCodeAuthenticationFilter")
public class SmsCodeAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 返回true代表不执行过滤器，false代表执行
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        // 登录提交的时候验证验证码
        if (pathMatcher.match("/api-auth/authentication/sms", request.getRequestURI())) {

            if (request.getParameter("grant_type")!=null){
                //短信模式模式需要验证码
                if("mobile".toUpperCase().equals(request.getParameter("grant_type").toUpperCase())){
                    return false;
                }

            }


        }
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            validateCodeService.validate(request);
        } catch (AuthenticationException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        chain.doFilter(request, response);
    }
}
