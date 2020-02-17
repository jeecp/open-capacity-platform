package com.open.capacity.concurrency;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
/**
 * 线程安全类
 */
@Slf4j
public class Test07 {

	private static int requestTotal = 5000;

	public static int concurrencyTotal = 1000;

	public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

 

	public static Date parse(String strDate)  {
		return DateTime.parse(strDate, dateTimeFormatter).toDate();
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
