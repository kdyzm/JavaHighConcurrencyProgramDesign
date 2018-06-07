package com.kdyzm.thread.thread040408;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 
 * @author kdyzm
 *
 */
public class LockFreeVector<E> {
	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets = null;

	static class Descriptor<E> {
		public int size;
		volatile WriteDescriptor<E> writeop;

		public Descriptor(int size, WriteDescriptor<E> writeop) {
			super();
			this.size = size;
			this.writeop = writeop;
		}

		public void completeWrite() {
			//TODO 
		}
	}

	static class WriteDescriptor<E> {
		public E oldV;
		public E newV;
		public AtomicReferenceArray<E> addr;
		public int addr_ind;

		public WriteDescriptor(E oldV, E newV, AtomicReferenceArray<E> addr, int addr_ind) {
			super();
			this.oldV = oldV;
			this.newV = newV;
			this.addr = addr;
			this.addr_ind = addr_ind;
		}

		public void doIt() {
			addr.compareAndSet(addr_ind, oldV, newV);
		}
	}
}
