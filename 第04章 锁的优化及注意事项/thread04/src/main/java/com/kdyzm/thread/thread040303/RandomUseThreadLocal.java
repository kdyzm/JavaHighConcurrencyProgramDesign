package com.kdyzm.thread.thread040303;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 不使用ThreadLocal产生100W个随机数
 * 和使用ThreadLocal产生100W个随机数
 * 效率比较：
 * 使用ThreadLocal效率要远高于不使用ThreadLocal
 * 
 * @author t_zhengrj
 *
 */
public class RandomUseThreadLocal {
	public static final int GET_COUNT = 100000;
	public static final int THREAD_COUNT = 4;

	static ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);

	public static Random random = new Random(123);

	public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>() {
		protected Random initialValue() {
			return new Random(123);
		};
	};

	public static class RandomTask implements Callable<Long> {

		private int mode = 0;

		public RandomTask(int mode) {
			this.mode = mode;
		}

		public Random getRandom(int mode) {
			if (mode == 0) {
				return random;
			} else if (mode == 1) {
				return tRnd.get();
			} else {
				return null;
			}
		}

		@Override
		public Long call() throws Exception {
			long b = System.currentTimeMillis();

			for (long i = 0; i < GET_COUNT; i++) {
				getRandom(mode).nextInt();
			}
			long e = System.currentTimeMillis();
			return e - b;
		}

		public static void main(String[] args) throws Exception, ExecutionException {
			Future<Long>[] futs = new Future[THREAD_COUNT];
			long totaltime = 0;
			
			
			// start
			for (int i = 0; i < THREAD_COUNT; i++) {
				futs[i] = es.submit(new RandomTask(0));
			}
			totaltime = 0;
			for (int i = 0; i < THREAD_COUNT; i++) {
				totaltime += futs[i].get();
			}
			System.out.println("多线程访问同一个Random示例：" + totaltime + "ms");
			// end
			
			// start
			for (int i = 0; i < THREAD_COUNT; i++) {
				futs[i] = es.submit(new RandomTask(1));
			}
			totaltime = 0;
			for (int i = 0; i < THREAD_COUNT; i++) {
				totaltime += futs[i].get();
			}
			System.out.println("使用ThreadLocal包装的Random示例：：" + totaltime + "ms");
			// end

			es.shutdown();
		}
	}

}
