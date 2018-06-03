package com.kdyzm.thread.thread.chapter020802;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList在并发环境下会出现线程安全性问题
 * 1.出现数组越界问题
 * 2.得到的最终结果小于预期值
 * 3.得到的结果和预期值相同（几率太小了，几乎不存在）
 * 
 * @author kdyzm
 *
 */
public class ArrayListDemo {
	private static List<Integer> list = new ArrayList<>();

	public static class MyThread implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 100000; i++) {
				list.add(i);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new MyThread());
		Thread t2 = new Thread(new MyThread());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(list.size());
	}
}
