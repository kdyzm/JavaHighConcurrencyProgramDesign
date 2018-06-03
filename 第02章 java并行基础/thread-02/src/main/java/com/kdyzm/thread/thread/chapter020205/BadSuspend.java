package com.kdyzm.thread.thread.chapter020205;

/**
 * 
 * @author kdyzm
 * 
 */
public class BadSuspend implements Runnable {

	private static final Object obj = new Object();

	@Override
	public void run() {
		synchronized (obj) {
			System.out.println(Thread.currentThread().getName());
			Thread.currentThread().suspend();
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new BadSuspend());
		t1.setName("t1");
		Thread t2 = new Thread(new BadSuspend());
		t2.setName("t2");
		t1.start();
		Thread.sleep(2000L);
		t2.start();
		t1.resume();
		t2.resume();
		t1.join();
		t2.join();
	}

}
