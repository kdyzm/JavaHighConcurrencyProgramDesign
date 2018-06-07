package com.kdyzm.thread.thread040403;

import sun.misc.Unsafe;

/**
 * Unsafe类不可以直接使用，会报出异常
 * @author kdyzm
 *
 */
public class UnSafeIsNotAvailable {
	public static void main(String[] args) {
		Unsafe unsafe = Unsafe.getUnsafe();
	}
}
