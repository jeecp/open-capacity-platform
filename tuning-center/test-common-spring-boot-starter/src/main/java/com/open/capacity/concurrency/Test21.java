package com.open.capacity.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 懒汉模式 重排序问题
 */
@Slf4j
public class Test21 {

	private Test21() {

	}

	public static volatile Test21 test21 = null;

	public static Test21 getInstance() {
		//双重同步锁
		if (test21 == null) {
			synchronized (Test21.class) {
				if (test21 == null) {
					test21 = new Test21();
				}
			}
		}
		

		return test21;
	}

	public static void main(String[] args) throws InterruptedException {
		int requestTotal = 5000;

		int concurrencyTotal = 1000;

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
					Thread.sleep(6000);
					System.out.println(Test21.getInstance().hashCode());
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
