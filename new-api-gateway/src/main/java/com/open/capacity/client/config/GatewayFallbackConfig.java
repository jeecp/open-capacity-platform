package com.open.capacity.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.open.capacity.client.handler.HystrixFallbackHandler;

@Configuration
public class GatewayFallbackConfig {
        @Autowired
    private HystrixFallbackHandler hystrixFallbackHandler;

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions.route(
            RequestPredicates.GET("/defaultfallback")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hystrixFallbackHandler);
    }

}
 