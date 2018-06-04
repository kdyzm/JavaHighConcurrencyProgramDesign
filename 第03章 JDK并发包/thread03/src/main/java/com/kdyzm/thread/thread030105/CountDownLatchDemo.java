package com.kdyzm.thread.thread030105;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的简单使用
 * 
 * @author t_zhengrj
 *
 */
public class CountDownLatchDemo implements Runnable {

	private static final CountDownLatch end = new CountDownLatch(10);

	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10) * 100);
			System.out.println(Thread.currentThread().getName() + " 准备完毕！");
			end.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		CountDownLatchDemo demo = new CountDownLatchDemo();
		for (int i = 0; i < 10; i++) {
			new Thread(demo).start();
		}

		end.await();
		System.out.println("fire");
	}

}
