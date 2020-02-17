package com.open.capacity.concurrency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Test06 {

	private static int requestTotal = 5000;

	public static int concurrencyTotal = 1000;

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

 
	public static Date parse(String strDate) throws ParseException {
		return sdf.parse(strDate);
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		final Semaphore semaphore = new Semaphore(concurrencyTotal);

		final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);

		String dateStr = "20191103100247";

		IntStream.rangeClosed(1, requestTotal).forEach((i) -> {

			executorService.execute(() -> {

				try {
					semaphore.acquire();
					parse(dateStr);
					semaphore.release();
				} catch (Exception e) {
					log.error("log exception : {}", e.getMessage());
				}
				countDownLatch.countDown();

			});

		});

		countDownLatch.await();
		executorService.shutdown();

	}
}
