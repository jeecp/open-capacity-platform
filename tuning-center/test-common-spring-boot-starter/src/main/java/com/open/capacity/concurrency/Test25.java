package com.open.capacity.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * lock 非重入锁
 */
@Slf4j
public class Test25 {

	// 请求总数
	private static int requestTotal = 5000;
	// 并发总数
	public static int concurrencyTotal = 1000;

	public static int count = 0;
	
	public  MyLock lock = new MyLock();
	

	public static Lock lock1 = new ReentrantLock();
	
	public   void add1() {
		try {
			lock.lock();
			System.out.println("a" + count);
			add2();
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
	}
	public   void add2() {
		try {
			lock.lock();
			System.out.println("b" + count);
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		Test25 test25 = new Test25();

		 new Thread(()-> test25.add1()).start();
		

	}
	
	static class MyLock implements Lock {

		private boolean isLocked = false ;

		@Override
		public synchronized void lock() {
			//自旋
			while(isLocked){
				try {
					wait();
				} catch (InterruptedException e) {
				}
				
				
				
			}
			isLocked = true ;
			count++ ;
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