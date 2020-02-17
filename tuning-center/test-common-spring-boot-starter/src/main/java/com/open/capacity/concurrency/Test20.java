package com.open.capacity.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 懒汉模式双重同步锁
 */
@Slf4j
public class Test20 {

	private Test20() {

	}

	public static Test20 test20 = null;

	public static Test20 getInstance() {
		//双重同步锁
		if (test20 == null) {
			synchronized (Test20.class) {
				if (test20 == null) {
					test20 = new Test20();
				}
			}
		}
		

		return test20;
	}

	public static void main(String[] args) throws InterruptedException {
		int requestTotal = 5000;

		int concurrencyTotal = 50;

		ExecutorService executorService = Executors.newCachedThreadPool();

		final Semaphore semaphore = new Semaphore(concurrencyTotal);

		final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);

		IntStream.rangeClosed(1, requestTotal).forEach((i) -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			executorService.execute(() -> {

				try {
					semaphore.acquire();
					System.out.println(Test20.getInstance().hashCode());
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
