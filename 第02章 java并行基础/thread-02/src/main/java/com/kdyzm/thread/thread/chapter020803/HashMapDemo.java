package com.kdyzm.thread.thread.chapter020803;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap线程安全性问题
 * 
 * @author kdyzm
 *
 *         1.程序正常结束，结果符合预期（小概率事件）<br/>
 *         2.程序正常结束，结果小于预期值<br/>
 *         3.程序永远无法结束（jdk1.7下有可能发生）
 */
public class HashMapDemo {
	private static Map<Integer, Integer> map = new HashMap<>();

	public static class MyThread implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 100000; i++) {
				map.put(i, i);
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
		System.out.println(map.size());
	}
}
