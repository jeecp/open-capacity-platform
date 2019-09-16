package com.open.capacity.eureka.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 只在测试环境下有效
 * 测试环境下，只能用@TestConfiguration，不能用@Configuration
 */
@TestConfiguration
public class TestEurekaConfiguration {

	@Bean
	public Runnable createRunable(){
		return () -> {};
	}
}
