package com.open.capacity.concurrency;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 同步容器
 */
@Slf4j
public class Test13 {

	private static int requestTotal = 5000;

	public static int concurrencyTotal = 1000;
	//同步容器
	public static Set set = Collections.synchronizedSet(Sets.newHashSet());

 

	public static void add(int i)  {
		set.add(i);
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
		log.info("count:{}", set.size());
		executorService.shutdown();

	}
}
