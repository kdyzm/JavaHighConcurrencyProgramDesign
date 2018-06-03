package com.kdyzm.thread.thread.chapter0203;

import java.util.ArrayList;
import java.util.List;

/**
 * volitile不能保证复合操作的原子性实例
 * 
 * @author kdyzm
 *
 */
public class VolatileMain {
	static volatile int sum = 0;

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			list.add(1);
		}
		list.parallelStream().forEach(x -> {
			sum += x;
		});
		System.out.println(sum);
	}
}
