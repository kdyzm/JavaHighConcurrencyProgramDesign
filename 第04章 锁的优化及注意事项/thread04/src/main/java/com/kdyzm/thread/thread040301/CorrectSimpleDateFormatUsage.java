package com.kdyzm.thread.thread040301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimpleDateFormat方法并非线程安全的，可以使用两种方式解决该线程安全性问题：
 * 1. 改造成同步方法
 * 2. 使用ThreadLocal类，人手一支笔
 * @author kdyzm
 */
public class CorrectSimpleDateFormatUsage implements Runnable {

	private static final ThreadLocal<SimpleDateFormat> t = new ThreadLocal<>();

	int i;

	public CorrectSimpleDateFormatUsage(int i) {
		this.i = i;
	}

	public static void main(String[] args) {
		ExecutorService pools = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 10000; i++) {
			pools.execute(new CorrectSimpleDateFormatUsage(i));
		}
		pools.shutdown();
	}

	@Override
	public void run() {
		try {
			SimpleDateFormat sdf = t.get();
			if (Objects.isNull(sdf)) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				t.set(sdf);
			}
			Date parse = sdf.parse("2018-03-21 00:00:" + i % 60);
			System.out.println(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
