package com.kdyzm.thread.thread030103;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量机制允许多个线程同时访问同一个资源
 * @author t_zhengrj
 *
 */
public class SemaPhoreDemo implements Runnable {

	static Semaphore sem = new Semaphore(5);

	@Override
	public void run() {
		try {
			sem.acquire();
			Thread.sleep(200L);
			System.out.println(Thread.currentThread().getId() + ":done!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			sem.release();
		}

	}

	public static void main(String[] args) {
		Executor pool = Executors.newFixedThreadPool(20);
		SemaPhoreDemo demo = new SemaPhoreDemo();
		for (int i = 0; i < 20; i++) {
			pool.execute(demo);
		}
	}

}
