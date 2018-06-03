package com.kdyzm.thread.thread.chapter0205;

/**
 * 守护线程
 * @author kdyzm
 *
 */
public class DaemonDemo extends Thread {

	@Override
	public void run() {
		while (true) {
			System.out.println("i am alive .");
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		DaemonDemo daemonDemo = new DaemonDemo();
		daemonDemo.setDaemon(true);// 设置守护线程必须放到start方法执行前
		daemonDemo.start();
		Thread.sleep(4000L);
	}
}
