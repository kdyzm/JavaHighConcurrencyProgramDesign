package com.kdyzm.thread.thread030106;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏的简单实用
 * 
 * @author t_zhengrj
 *
 */
public class CyclicBarrierDemo {
	public static class Soldier implements Runnable {

		private String name;
		private CyclicBarrier cb;

		public Soldier() {
		}

		public Soldier(String name, CyclicBarrier cb) {
			this.name = name;
			this.cb = cb;
		}

		@Override
		public void run() {
			try {
				cb.await();// 集合等待
				doWork();
				cb.await();// 任务等待
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		void doWork() {
			try {
				Thread.sleep(100);
				System.out.println(name + " 完成工作！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class BarrierRun implements Runnable {
		boolean flag;
		int N;

		public BarrierRun(boolean flag, int N) {
			this.flag = flag;
			this.N = N;
		}

		@Override
		public void run() {
			if (flag) {
				System.out.println("司令：士兵【" + N + "】个，任务完成!");
			} else {
				System.out.println("司令：士兵【" + N + "】个，集合完毕!");
				flag = true;
			}
		}
	}

	public static void main(String[] args) {
		final int N = 10;
		Thread[] allSoldier = new Thread[N];
		boolean flag = false;
		CyclicBarrier cb = new CyclicBarrier(N, new BarrierRun(flag, N));
		System.out.println("集合队伍！");
		for (int i = 0; i < N; i++) {
			System.out.println("士兵" + i + "报道！");
			allSoldier[i] = new Thread(new Soldier("士兵" + i, cb));
			allSoldier[i].start();
		}
	}

}
