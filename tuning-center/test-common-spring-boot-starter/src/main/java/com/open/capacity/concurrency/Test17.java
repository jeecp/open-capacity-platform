package com.open.capacity.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * cas 比较交互更新
 */
@Slf4j
public class Test17 {

	 
	//计数器
	public static AtomicInteger count = new AtomicInteger(0);
 

	public static void main(String[] args) {
		
		count.compareAndSet(0, 2);
		count.compareAndSet(1, 4);
		count.compareAndSet(2, 6);
		count.compareAndSet(3, 7);
		System.out.println(count);
	}
}
