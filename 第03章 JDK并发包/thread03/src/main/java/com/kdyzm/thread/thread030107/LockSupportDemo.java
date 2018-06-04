package com.kdyzm.thread.thread030107;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo implements Runnable {

	private static final Object obj = new Object();

	@Override
	public void run() {
		synchronized (obj) {
			System.out.println(Thread.currentThread().getName() + " start");
			// Thread.currentThread().suspend();
			LockSupport.park();
			System.out.println(Thread.currentThread().getName() + " end");
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new LockSupportDemo());
		t1.setName("t1");
		Thread t2 = new Thread(new LockSupportDemo());
		t2.setName("t2");
		t1.start();
		Thread.sleep(2000L);
		t2.start();
		// t1.resume();
		LockSupport.unpark(t1);
		// t2.resume();
		LockSupport.unpark(t2);
		t1.join();
		t2.join();
	}

}
