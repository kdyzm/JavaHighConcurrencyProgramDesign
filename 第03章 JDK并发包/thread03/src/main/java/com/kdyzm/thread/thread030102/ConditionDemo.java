package com.kdyzm.thread.thread030102;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个简单的使用Condition的例子
 * 
 * @author kdyzm <br/>
 *         1. 注意condition需要使用方法await，不是Object类中的wait方法实现线程等待<br/>
 *         2. ArrayBlockingQueue使用重入锁实现<br/>
 * 
 *         {@link java.util.concurrent.ArrayBlockingQueue<E>}
 */
public class ConditionDemo implements Runnable {

	private static final Lock lock = new ReentrantLock();
	private static final Condition condition = lock.newCondition();

	@Override
	public void run() {
		lock.lock();
		System.out.println("start");
		try {
			condition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end");
		lock.unlock();
	}

	public static void main(String[] args) throws Exception {
		Thread t = new Thread(new ConditionDemo());
		t.start();
		Thread.sleep(500L);
		lock.lock();
		condition.signalAll();
		lock.unlock();
		System.out.println("main end");
	}

}
