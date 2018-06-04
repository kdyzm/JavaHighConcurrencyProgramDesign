package com.kdyzm.thread.thread030208;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 堆栈去哪儿了：在线程池中寻找堆栈<br/>
 * 以下结果中，少了一个输出，实际上抛出了异常但是异常信息被吃掉了<br/>
 * 使用execute方法可以打印出来异常信息，使4用submit方法打印不出来堆栈信息<br/>
 * @author kdyzm
 *
 */
public class DivTask implements Runnable {

	int a, b;

	public DivTask(int a, int b) {
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
		ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS,
				new SynchronousQueue<>());

		for (int i = 0; i < 5; i++) {
//			pool.submit(new DivTask(100, i));
			pool.execute(new DivTask(100, i));
		}
	}

}
