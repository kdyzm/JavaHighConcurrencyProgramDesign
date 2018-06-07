package com.kdyzm.thread.thread040302;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal的垃圾回收
 * 
 * @author t_zhengrj
 *
 */
public class ThreadLocalDemo_GC {
	static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected void finalize() throws Throwable {
			System.out.println(this.toString() + " is gc.");
		}
	};

	static volatile CountDownLatch cd = new CountDownLatch(10000);

	public static class ParseDate implements Runnable {

		private int i;

		public ParseDate(int i) {
			super();
			this.i = i;
		}

		@Override
		public void run() {
			SimpleDateFormat sdf = t1.get();
			if (Objects.isNull(sdf)) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
					@Override
					protected void finalize() throws Throwable {
						System.out.println(this.toString() + " is gc.");
					}
				};
				t1.set(sdf);
				System.out.println(Thread.currentThread().getId() + " create simpleDateFormat.");
			}
			try {
				Date t = t1.get().parse("2018-05-05 12:00:" + i % 60);
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				cd.countDown();
			}
		}

		@Override
		protected void finalize() throws Throwable {
			System.out.println(this + " is gc.");
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		System.out.println("mission complete.");
		t1 = null;
		System.gc();
		System.out.println("first gc is complete.");

		t1 = new ThreadLocal<SimpleDateFormat>();
		cd = new CountDownLatch(10000);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		Thread.sleep(1000);
		System.gc();
		System.out.println("second gc complete.");
	}
}
