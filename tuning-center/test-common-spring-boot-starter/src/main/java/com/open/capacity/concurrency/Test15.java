package com.open.capacity.concurrency;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;
/**
 * 并发容器
 */
@Slf4j
public class Test15 {

	private static int requestTotal = 5000;

	public static int concurrencyTotal = 1000;

	//并发容器 
	public static Map<Integer, Integer> map =  Maps.newConcurrentMap();

 

	public static void add(int i)  {
		map.put(i, i);
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		final Semaphore semaphore = new Semaphore(concurrencyTotal);

		final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);


		IntStream.rangeClosed(1, requestTotal).forEach((i) -> {

			executorService.execute(() -> {

				try {
					semaphore.acquire();
					add( i);
					semaphore.release();
				} catch (Exception e) {
					log.error("log exception : {}", e.getMessage());
				}
				countDownLatch.countDown();

			});

		});

		countDownLatch.await();
		log.info("count:{}", map.size());
		executorService.shutdown();

	}
}
