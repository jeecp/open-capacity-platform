package com.open.capacity.concurrency;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
/**
 * 同步容器
 */
@Slf4j
public class Test10 {

	private static int requestTotal = 5000;

	public static int concurrencyTotal = 1000;

	//同步容器
	public static List list = Collections.synchronizedList(Lists.newArrayList());

 

	public static void add(int i )  {
		list.add(i);
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		final Semaphore semaphore = new Semaphore(concurrencyTotal);

		final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);


		IntStream.rangeClosed(1, requestTotal).forEach((i) -> {

			executorService.execute(() -> {

				try {
					semaphore.acquire();
					add( i );
					semaphore.release();
				} catch (Exception e) {
					log.error("log exception : {}", e.getMessage());
				}
				countDownLatch.countDown();

			});

		});

		countDownLatch.await();
		log.info("count:{}", list.size());
		executorService.shutdown();

	}
}
