package com.open.capacity.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
/**
 * 饥饿模式
 */
@Slf4j
public class Test18 {

	 private Test18(){
		 
	 }
	 
	 
	 public static Test18 test18 = new Test18();
	 
	 public static Test18 getInstance(){
		 return test18 ;
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
					System.out.println(Test18.getInstance().hashCode());
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
