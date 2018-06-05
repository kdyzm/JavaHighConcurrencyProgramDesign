package com.kdyzm.thread.thread030307;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 跳表的简单使用
 * @author t_zhengrj
 *
 */
public class SkipListDemo {
	public static void main(String[] args) {
		Map<Integer, Integer> data = new ConcurrentSkipListMap<>();
		for (int i = 10; i > 0; i--) {
			data.put(i, i);
		}
		for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
