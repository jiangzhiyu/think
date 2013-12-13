package com.qihoo.dlc.think.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.qihoo.dlc.annotation.ThreadSafe;
import com.qihoo.dlc.annotation.ThreadUnsafe;

public class EvenGenerator extends IntGenerator {

	@ThreadUnsafe
	private int currentEvenValue = 0;
	private Lock lock = new ReentrantLock();

	@Override
	@ThreadSafe
	public int next() {
		lock.lock();
		try {
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator(), 4);
	}

}
