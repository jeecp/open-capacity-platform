/**
 * 
 */
package com.open.capacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年11月12日 上午22:57:51 类说明
 */

@Configuration
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
public class GatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}

}
