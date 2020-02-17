package com.open.capacity.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Test27 {
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private static Map<Object, Object> map = new HashMap<>();
	private static ReadLock readLock = rwl.readLock();
	private static WriteLock writeLock = rwl.writeLock();

	public static Object get(String key) {

		try {
			readLock.lock();
			System.out.println("正在做读的操作,key:" + key + "开始..");
			Thread.sleep(50);
			Object object = map.get(key);
			System.out.println("正在做读的操作,key:" + key + "结束..");
			return object;
		} catch (Exception e) {
			return null;
		}finally {
			readLock.unlock();
		}
		
	
	}

	public static void put(String key, Object value) {
		try {
			writeLock.lock();
			System.out.println("正在做写的操作,key:" + key + ",value:" + value + "开始..");
				Thread.sleep(50);
			map.put(key, value);
			System.out.println("正在做写的操作,key:" + key + ",value:" + value + "結束..");
		} catch (Exception e) {
			
		}finally {
			writeLock.unlock();
		}
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					Test27.put(i + "", "i:" + i);
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					Object object = Test27.get(i + "");
				}
			}
		}).start();
	}

}
