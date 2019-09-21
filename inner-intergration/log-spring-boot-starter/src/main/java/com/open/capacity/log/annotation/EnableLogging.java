package com.open.capacity.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.open.capacity.log.selector.LogImportSelector;



/**
 * 启动日志框架支持
 * @author owen
 * @create 2017年7月2日
 * 自动装配starter ，需要配置多数据源
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogImportSelector.class)
public @interface EnableLogging{
//	String name() ;
}