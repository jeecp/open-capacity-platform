package com.open.capacity.common.feign;

import org.springframework.context.annotation.Bean;

import feign.Logger.Level;

public class GolbalFeignConfig {

	@Bean
	public Level levl(){
		return Level.FULL;
	}
}
