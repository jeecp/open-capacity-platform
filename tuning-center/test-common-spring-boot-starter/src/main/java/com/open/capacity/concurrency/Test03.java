package com.open.capacity.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * static属性cas
 */
@Slf4j
public class Test03 {

	// 请求总数
	private static int requestTotal = 5000;
	// 并发总数
	public static int concurrencyTotal = 1000;
	public static AtomicInteger count = new AtomicInteger(0);

	public static void add() {
		count.addAndGet(1);
	}

	public static void main(String[] args) {

		try {
			ExecutorService executorService = Executors.newCachedThreadPool();

			final Semaphore semaphore = new Semaphore(concurrencyTotal);

			final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);

			IntStream.rangeClosed(1, requestTotal).forEach((i) -> {

				executorService.execute(() -> {

					try {
						semaphore.acquire();
						add();
						semaphore.release();
					} catch (Exception e) {
						log.error("log exception : {}", e.getMessage());
					}
					countDownLatch.countDown();

				});

			});

			countDownLatch.await();
			log.info("count:{}", count.get());
			executorService.shutdown();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
