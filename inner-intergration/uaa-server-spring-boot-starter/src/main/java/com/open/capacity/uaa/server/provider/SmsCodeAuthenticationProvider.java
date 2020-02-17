package com.open.capacity.uaa.server.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.open.capacity.common.token.SmsCodeAuthenticationToken;

@Component
public class SmsCodeAuthenticationProvider implements  AuthenticationProvider {

    @Autowired(required = false)
    @Qualifier("smsUserDetailServiceImpl")
    private UserDetailsService smsUserDetailServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken) authentication;

        UserDetails userDetails = smsUserDetailServiceImpl.loadUserByUsername((String) token.getPrincipal());

        if (userDetails == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        // 需要把未认证中的一些信息copy到已认证的token中
        authenticationResult.setDetails(token);
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
