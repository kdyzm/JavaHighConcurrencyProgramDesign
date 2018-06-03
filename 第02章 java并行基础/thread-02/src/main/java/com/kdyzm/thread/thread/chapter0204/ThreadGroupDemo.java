package com.kdyzm.thread.thread.chapter0204;

/**
 * 线程组的简单使用
 * @author kdyzm
 *
 */
public class ThreadGroupDemo implements Runnable {

	public static void main(String[] args) {
		ThreadGroup tg = new ThreadGroup("kdyzm");
		Thread t1 = new Thread(tg, new ThreadGroupDemo(), "t1");
		Thread t2 = new Thread(tg, new ThreadGroupDemo(), "t2");
		t1.start();
		t2.start();
		System.out.println(tg.activeCount());
		tg.list();
	}

	@Override
	public void run() {
		String groupAndName = Thread.currentThread().getThreadGroup().getName() + "-"
				+ Thread.currentThread().getName();
		while (true) {
			System.out.println("i am " + groupAndName);
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
