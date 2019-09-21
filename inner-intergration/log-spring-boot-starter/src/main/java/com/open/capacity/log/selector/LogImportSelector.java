package com.open.capacity.log.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author owen
 * log-spring-boot-starter 自动装配 
 */


public class LogImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// TODO Auto-generated method stub
//		importingClassMetadata.getAllAnnotationAttributes(EnableEcho.class.getName());
		
		
		
		
		return new String[] { 
				"com.open.capacity.log.aop.LogAnnotationAOP",
				"com.open.capacity.log.service.impl.LogServiceImpl",
				"com.open.capacity.log.config.LogAutoConfig"
				
		};
	}

}