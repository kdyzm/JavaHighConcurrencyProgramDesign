package com.kdyzm.thread.thread.chapter020804;

/**
 * HashMap线程安全性问题
 * 
 * @author kdyzm <br/>
 *         这是典型的错误用法，由于Integer类型使用的是不变模式，所以会产生线程安全性问题
 */
public class IntegerDemo implements Runnable {
	public static Integer i = 0;

	@Override
	public void run() {
		for (int j = 0; j < 100000; j++) {
			synchronized (i) {
				i++;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new IntegerDemo());
		Thread t2 = new Thread(new IntegerDemo());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
