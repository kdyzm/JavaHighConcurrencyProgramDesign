package com.kdyzm.thread.thread040301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimpleDateFormat方法并非线程安全的
 * @author kdyzm
 */
public class WrongSimpleDateFormatUsage implements Runnable {

	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	int i;

	public WrongSimpleDateFormatUsage(int i) {
		this.i = i;
	}

	public static void main(String[] args) {
		ExecutorService pools = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 10000; i++) {
			pools.execute(new WrongSimpleDateFormatUsage(i));
		}
	}

	@Override
	public void run() {
		try {
			Date parse = sdf.parse("2018-03-21 00:00:" + i % 60);
//			System.out.println(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
