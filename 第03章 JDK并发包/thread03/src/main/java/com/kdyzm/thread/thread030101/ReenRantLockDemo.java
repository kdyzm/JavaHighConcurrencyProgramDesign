package com.kdyzm.thread.thread030101;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁简单示例代码
 * 
 * @author kdyzm
 *
 */
public class ReenRantLockDemo implements Runnable {

	private static Integer i = 0;

	private static final ReentrantLock obj = new ReentrantLock();

	@Override
	public void run() {
		for (int j = 0; j < 10000; j++) {
			obj.lock();
			i++;
			obj.unlock();
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new ReenRantLockDemo());
		Thread t2 = new Thread(new ReenRantLockDemo());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
