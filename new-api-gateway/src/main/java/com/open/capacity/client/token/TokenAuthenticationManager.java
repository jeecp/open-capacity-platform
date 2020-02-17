package com.open.capacity.client.token;

import org.springframework.http.HttpStatus;

/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;

import reactor.core.publisher.Mono;

/**
 * A {@link ReactiveAuthenticationManager} for Jwt tokens.
 *
 * @author Rob Winch
 * @since 5.1
 */
public final class TokenAuthenticationManager implements ReactiveAuthenticationManager {
	private TokenStore tokenStore;

    public TokenAuthenticationManager(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessTokenValue -> {
                    OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);
                    if (accessToken == null) {
                    	OAuth2Error error = new BearerTokenError(
                				BearerTokenErrorCodes.INVALID_TOKEN,
                				HttpStatus.UNAUTHORIZED,
                				"Invalid access token: " + accessTokenValue,
                				"https://tools.ietf.org/html/rfc6750#section-3.1");
                        return Mono.error(new OAuth2AuthenticationException(error,"Invalid access token: " + accessTokenValue));
                    } else if (accessToken.isExpired()) {
                        tokenStore.removeAccessToken(accessToken);
                        OAuth2Error error = new BearerTokenError(
                				BearerTokenErrorCodes.INVALID_TOKEN,
                				HttpStatus.UNAUTHORIZED,
                				"Access token expired: " + accessTokenValue,
                				"https://tools.ietf.org/html/rfc6750#section-3.1");
                        
                        return  Mono.error(new OAuth2AuthenticationException(error,"Access token expired: " + accessTokenValue));
                    }

                    OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
                    if (result == null) {
                        return Mono.error(new InvalidTokenException("Invalid access token: " + accessTokenValue));
                    }
                    return Mono.just(result);
                }))
                .cast(Authentication.class);
    }
}
