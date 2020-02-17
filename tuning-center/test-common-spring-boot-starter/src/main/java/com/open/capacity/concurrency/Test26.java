package com.open.capacity.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * lock 重入锁
 */
@Slf4j
public class Test26 {

	 public static int count = 0 ;
	
	public  MyLock lock = new MyLock();
	

	public static Lock lock1 = new ReentrantLock();
	
	public    void add1() {
		try {
			lock.lock();
			System.out.println("a"+count);
			add2();
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
	}
	
	public    void add2() {
		try {
			lock.lock();
			System.out.println("b"+count);
		} catch (Exception e) {
		}finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {

		Test26 test26 = new Test26();

		 new Thread(()-> test26.add1()).start();
	}
	
	static class MyLock implements Lock {

		private boolean isLocked = false ;
		
		private Thread lockBy ;
		
		private int lockCnt = 0 ;
		
		@Override
		public synchronized void lock() {
			
			Thread currentThread =Thread.currentThread();
			
			//自旋
			while(isLocked && lockBy != currentThread){
				try {
					wait();
				} catch (InterruptedException e) {
				}
				
				
				
			}
			isLocked = true ;
			lockBy =  currentThread ;
			lockCnt ++ ;
			count++ ;
		}

		@Override
		public  synchronized void unlock() {
			// TODO Auto-generated method stub
			
			Thread currentThread =Thread.currentThread();
			
			if(currentThread == lockBy) {
				lockCnt  -- ;
				
				if(lockCnt ==0 ){
					isLocked = false ;
					this.notify();
				}
				
			}
			
			
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