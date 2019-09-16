package com.open.capacity.uaa.server.token;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class ResJwtAccessTokenConverter extends JwtAccessTokenConverter{
	
	
	public ResJwtAccessTokenConverter() {
		super();
		super.setAccessTokenConverter(new JwtUserAuthenticationConverter());
	}
	public  static class JwtUserAuthenticationConverter extends DefaultAccessTokenConverter {
		
		public  JwtUserAuthenticationConverter() {
			super.setUserTokenConverter(new JWTfaultUserAuthenticationConverter());
		}
		public static class JWTfaultUserAuthenticationConverter extends DefaultUserAuthenticationConverter{
			
			public Map<String, ?> convertUserAuthentication(Authentication authentication) {
				Map<String, Object> response = new LinkedHashMap<String, Object>();
				response.put(USERNAME, authentication.getPrincipal());
//				if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
//					response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
//				}
				return response;
			}
		}
	}
}
