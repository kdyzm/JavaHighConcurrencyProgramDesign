package com.kdyzm.thread.thread.chapter020204;

/**
 * 简单的等待通知方法的使用(wait/notify)
 * 
 * @author kdyzm
 *
 */
public class SimpleWaitAndNotify {

	static final Object obj = new Object();

	public static class T1 implements Runnable {

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println("T1 start");
				try {
					obj.wait();
				} catch (InterruptedException e) {

				}
				System.out.println("T1 end");
			}
		}

	}

	public static class T2 implements Runnable {

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println("T2 start");
				obj.notify();
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
				}
				System.out.println("T2 end");
			}
		}
	}

	public static void main(String[] args) {
		T1 t1 = new T1();
		T2 t2 = new T2();
		new Thread(t1).start();
		new Thread(t2).start();

	}
}
