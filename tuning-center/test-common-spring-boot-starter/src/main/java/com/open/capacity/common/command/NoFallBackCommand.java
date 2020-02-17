package com.open.capacity.common.command;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class NoFallBackCommand extends HystrixCommand<String> {

	private final String name;

	public NoFallBackCommand(String name) {

		// //最少配置:指定命令组名(CommandGroup)

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("NoFallBackCommand"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("query"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ExampleThreadPool"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(20))// 服务线程池数量
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerErrorThresholdPercentage(60) // 熔断器关闭到打开阈值
						.withCircuitBreakerSleepWindowInMilliseconds(3000)// 熔断器打开到关闭的时间窗长度

		));

		this.name = name;

	}

	@Override

	protected String run() {

		// 依赖逻辑封装在run()方法中

		return "Hello " + name + ", thread: " + Thread.currentThread().getName();

	}

	// 调用实例

	public static void main(String[] args) throws Exception {

		// 每个Command对象只能调用一次,不可以重复调用

		NoFallBackCommand helloWorldCommand = new NoFallBackCommand("Synchronous-hystrix");

		// 使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();

		String result = helloWorldCommand.execute();

		System.out.println("result = " + result);

		helloWorldCommand = new NoFallBackCommand("Asynchronous-hystrix");

		// 异步调用,可自由控制获取结果时机,

		Future<String> future = helloWorldCommand.queue();

		// get操作不能超过command定义的超时时间,默认:1秒

		result = future.get(100, TimeUnit.MILLISECONDS);

		System.out.println("result = " + result);

		System.out.println("mainThread = " + Thread.currentThread().getName());

	}

}
