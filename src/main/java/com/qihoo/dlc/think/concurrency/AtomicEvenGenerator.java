package com.qihoo.dlc.think.concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import com.qihoo.dlc.annotation.ThreadSafe;

public class AtomicEvenGenerator extends IntGenerator {
	@ThreadSafe
	private AtomicInteger currentEvenValue = new AtomicInteger(0);

	@Override
	@ThreadSafe
	public int next() {
		return currentEvenValue.addAndGet(2);
	}

	public static void main(String[] agrs) throws InterruptedException {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.err.println("Aborting");
				System.exit(0);
			}
		}, 5000);
		EvenChecker.test(new AtomicEvenGenerator());
	}

}
