//package com.open.capacity.log.test.config;
//
//import java.util.concurrent.Executor;
//
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Role;
//import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//@Configuration
//@EnableAutoConfiguration
//@EnableAsync
//// add the infrastructure role to ensure that the bean gets auto-proxied
//@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//public class CustomExecutorConfig extends AsyncConfigurerSupport {
//
//    @Autowired
//    BeanFactory beanFactory;
//
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // CUSTOMIZE HERE
//        executor.setCorePoolSize(7);
//        executor.setMaxPoolSize(42);
//        executor.setQueueCapacity(11);
//        executor.setThreadNamePrefix("MyExecutor-");
//        // DON'T FORGET TO INITIALIZE
//        executor.initialize();
//        return new LazyTraceExecutor(this.beanFactory, executor);
//    }
//
//}