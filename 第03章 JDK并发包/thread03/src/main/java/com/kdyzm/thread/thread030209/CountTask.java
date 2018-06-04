package com.kdyzm.thread.thread030209;

import java.util.concurrent.RecursiveTask;

/**
 * <p>
 * ForkJoinTask有两种子类：<br/>
 * {@link java.util.concurrent.RecursiveAction} <br/>
 * 以及<b/> 
 * {@link java.util.concurrent.RecursiveTask<V>} <br/>
 * 分别是不带有返回值和带有返回值的任务<br/>
 * 以下是使用ForkJoin方法计算数列求和的简单演示<br/>
 * </p>
 * 
 * @author kdyzm
 *
 */
public class CountTask extends RecursiveTask<Long> {

	private static final int THRESHOLD = 10000;
	
	private long start;
	private long end;
	
	@Override
	protected Long compute() {

		return null;
	}

}
