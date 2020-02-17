package com.open.capacity.common.command;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class FallBackCommand extends HystrixCommand<String> {

	private final String name;

	public FallBackCommand(String name) {

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FallBackCommandGroup"))
				/* 依赖超时时间,1秒 */
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)));
		this.name = name;

	}

	@Override

	protected String getFallback() {

		return "exeucute Falled";

	}

	@Override

	protected String run() throws Exception {
		// sleep 5 秒,调用会超时
		TimeUnit.MILLISECONDS.sleep(5000);
		return "Hello " + name + " thread:" + Thread.currentThread().getName();

	}

	public static void main(String[] args) throws Exception {

		FallBackCommand command = new FallBackCommand("test-Fallback");
		String result = command.execute();
		System.out.println(result);
	}

}
