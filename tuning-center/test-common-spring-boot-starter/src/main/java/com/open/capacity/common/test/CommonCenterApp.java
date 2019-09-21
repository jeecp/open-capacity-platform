/**
 * 
 */
package com.open.capacity.common.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/** 
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2018年4月5日 下午19:52:21
* 类说明 
*/
 
@Configuration
@SpringBootApplication
public class CommonCenterApp {
	public static void main(String[] args) {
//		固定端口启动
//		SpringApplication.run(UserCenterApp.class, args);
		
		//随机端口启动
		SpringApplication app = new SpringApplication(CommonCenterApp.class);
		
		ConfigurableApplicationContext context = app.run(args);
		
		
		
		
	}

}
