package com.open.capacity.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

/**
 * lock
 */
@Slf4j
public class Test24 {

	// 请求总数
	private static int requestTotal = 5000;
	// 并发总数
	public static int concurrencyTotal = 1000;

	public static int count = 0;
	
	public static MyLock lock = new MyLock();
	

	public static Lock lock1 = new ReentrantLock();
	
	public static  void add() {
		try {
			lock.lock();
			count++;
			
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
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
			log.info("count:{}", count);
			executorService.shutdown();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static class MyLock implements Lock {

		private boolean isLocked = false ;

		@Override
		public synchronized void lock() {

			while(isLocked){
				try {
					wait();
				} catch (InterruptedException e) {
				}
				
				
				
			}
			isLocked = true ;
			
		}

		@Override
		public  synchronized void unlock() {
			// TODO Auto-generated method stub
			isLocked = false ;
			this.notify();
		}
		
		@Override
		public void lockInterruptibly() throws InterruptedException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean tryLock() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			// TODO Auto-generated method stub
			return false;
		}

		

		@Override
		public Condition newCondition() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}