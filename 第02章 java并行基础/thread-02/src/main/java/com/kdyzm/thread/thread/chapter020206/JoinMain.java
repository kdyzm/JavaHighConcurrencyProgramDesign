package com.kdyzm.thread.thread.chapter020206;

/**
 * join方法的简单实用
 * @author kdyzm
 *
 */
public class JoinMain extends Thread {

	private static volatile int i = 0;

	@Override
	public void run() {
		System.out.println("start");
		for (i = 0; i < 10000; i++)
			;
		System.out.println("end");
	}

	public static void main(String[] args) throws Exception {
		JoinMain joinMain = new JoinMain();
		joinMain.start();
		joinMain.join();
		System.out.println("final result=" + i);
	}
}
