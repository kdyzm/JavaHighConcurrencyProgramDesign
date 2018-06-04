package com.kdyzm.thread.thread030104;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWiteLockDemo {
	private static Lock lock = new ReentrantLock();

	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private static Lock readLock = readWriteLock.readLock();

	private static Lock writeLock = readWriteLock.writeLock();

	public void handleRead(Lock lock) {
		lock.lock();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void handleWrite(Lock lock) {
		lock.lock();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws Exception {
		final ReadWiteLockDemo demo = new ReadWiteLockDemo();

		Runnable read = new Runnable() {

			@Override
			public void run() {
				demo.handleRead(readLock);
			}
		};
		Runnable write = new Runnable() {

			@Override
			public void run() {
				demo.handleWrite(writeLock);
			}
		};

		for (int i = 0; i < 18; i++) {
			Thread thread = new Thread(read);
			thread.start();
		}

		for (int j = 0; j < 18; j++) {
			Thread thread = new Thread(write);
			thread.start();
		}

	}

}
