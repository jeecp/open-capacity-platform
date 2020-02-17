package com.open.capacity.client.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

import com.open.capacity.common.constant.UaaConstant;

public class TokenUtil {
	public  static String extractToken(ServerHttpRequest request) {
		List<String> strings = request.getHeaders().get(UaaConstant.Authorization);
		String authToken = "";
		if (strings != null) {
			authToken = strings.get(0).substring("Bearer".length()).trim();
		}

		if (StringUtils.isBlank(authToken)) {
			strings = request.getQueryParams().get(UaaConstant.TOKEN_PARAM);
			if (strings != null) {
				authToken = strings.get(0);
			}
		}

		return authToken;
	}
}
