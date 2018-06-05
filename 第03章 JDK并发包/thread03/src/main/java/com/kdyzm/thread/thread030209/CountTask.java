package com.kdyzm.thread.thread030209;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * <p>
 * ForkJoinTask有两种子类：<br/>
 * {@link java.util.concurrent.RecursiveAction} <br/>
 * 以及<b/> {@link java.util.concurrent.RecursiveTask<V>} <br/>
 * 分别是不带有返回值和带有返回值的任务<br/>
 * 以下是使用ForkJoin方法计算数列求和的简单演示<br/>
 * </p>
 * 
 * @author kdyzm
 *
 */
public class CountTask extends RecursiveTask<Long> {

	private static final long serialVersionUID = -825497145336580688L;

	private static final int THRESHOLD = 10000;

	private long start;
	private long end;

	public CountTask(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long sum = 0;
		boolean canCompute = (end - start) < THRESHOLD;
		if (canCompute) {
			for (long i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 分成100个小任务
			long step = (start + end) / 100;//??step=(end-start)/100
			List<CountTask> subTasks = new ArrayList<>();
			long pos = start;
			for (int i = 0; i < 100; i++) {
				long lastOne = pos + step;
				if (lastOne > end) {
					lastOne = end;
				}
				CountTask subTask = new CountTask(pos, lastOne);
				pos += step;
				subTasks.add(subTask);
				subTask.fork();
			}
			for (CountTask t : subTasks) {
				sum += t.join();
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool forJoinPool = new ForkJoinPool();
		CountTask task = new CountTask(0, 20000000L);
		ForkJoinTask<Long> result = forJoinPool.submit(task);
		try {
			long res = result.get();
			System.out.println("sum=" + res);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
