package com.open.capacity.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程不安全类
 */
@Slf4j
public class Test04 {

	// 请求总数
	private static int requestTotal = 5000;
	// 并发总数
	public static int concurrencyTotal = 1000;

	public static StringBuilder sb = new StringBuilder();

	public static void incr() {
		sb.append("1");
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		final Semaphore semaphore = new Semaphore(concurrencyTotal);

		final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);

		IntStream.rangeClosed(1, requestTotal).forEach((i) -> {

			executorService.execute(() -> {

				try {
					semaphore.acquire();
					incr();
					semaphore.release();
				} catch (Exception e) {
					log.error("log exception : {}", e.getMessage());
				}
				countDownLatch.countDown();

			});

		});

		countDownLatch.await();
		log.info("count:{}", sb.length());
		executorService.shutdown();

	}
}
