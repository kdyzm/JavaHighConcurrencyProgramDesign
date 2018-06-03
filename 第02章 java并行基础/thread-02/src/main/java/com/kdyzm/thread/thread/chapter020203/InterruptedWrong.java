package com.kdyzm.thread.thread.chapter020203;

public class InterruptedWrong implements Runnable {

	@Override
	public void run() {
		while (true) {
			Thread.yield();
			System.out.println("Hello,world!");
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t = new Thread(new InterruptedWrong());
		t.start();
		Thread.sleep(2000);
		t.interrupt();//无效的线程终止方法
	}

}
