package com.kdyzm.thread.thread.chapter020203;

/**
 * 线程睡眠时被中断会产生异常
 * @author kdyzm
 *
 */
public class InterruptedCorrect implements Runnable {

	@Override
	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("thread is interrupted.");
				break;
			}
			System.out.println("Hello,kdyzm!");
			try {
				Thread.sleep(2000L);
			} catch (Exception e) {
				System.out.println("interrupted when sleep");
				// 设置中断状态
				Thread.currentThread().interrupt();
			}
			Thread.yield();
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t = new Thread(new InterruptedCorrect());
		t.start();
		Thread.sleep(2000);
		t.interrupt();
	}

}
