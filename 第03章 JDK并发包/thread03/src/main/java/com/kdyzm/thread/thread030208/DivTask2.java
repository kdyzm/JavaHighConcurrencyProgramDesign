package com.kdyzm.thread.thread030208;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 相对于DivTask，这里仅仅变动了使用了新的线程池 {@link TraceThreadPoolExecutor}<br/>
 * 使用新的线程池之后，打印出了原始调用的堆栈信息
 * 
 * @author kdyzm
 *
 */
public class DivTask2 implements Runnable {

	int a, b;

	public DivTask2(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		double result = a / b;
		System.out.println(result);
	}

	public static void main(String[] args) {
		TraceThreadPoolExecutor pool = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS,
				new SynchronousQueue<>());

		for (int i = 0; i < 5; i++) {
			// pool.submit(new DivTask(100, i));
			pool.execute(new DivTask2(100, i));
		}
	}

}
