package com.open.capacity.common.async;

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

// https://stackoverflow.com/questions/23732089/how-to-enable-request-scope-in-async-task-executor
// 传递RequestAttributes and MDC
// SecurityContext
public class ContextCopyingDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        try {
			RequestAttributes context = RequestContextHolder.currentRequestAttributes(); 
			Map<String,String> previous = MDC.getCopyOfContextMap(); 
			SecurityContext securityContext = SecurityContextHolder.getContext();// 1
			return () -> {
			    try {
			    	if(previous==null){
			    		MDC.clear();
			    	}else{
			    		MDC.setContextMap(previous);
			    	}
			    	
			        RequestContextHolder.setRequestAttributes(context);
			        SecurityContextHolder.setContext(securityContext);// 2
			        runnable.run();
			    } finally {
			        RequestContextHolder.resetRequestAttributes();
			        // 清除操作
			        SecurityContextHolder.clearContext();// 3

			        if(previous==null){
			    		MDC.clear();
			    	}else{
			    		MDC.setContextMap(previous);
			    	}
			    }
			};
		} catch (IllegalStateException e) {
			return runnable;
		}
    }
}
