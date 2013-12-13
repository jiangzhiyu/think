package com.qihoo.dlc.think.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Car {
	private boolean waxOn = false;
	private boolean waxing = false;
	private boolean buffing = false;

	public synchronized void waxed() {
		waxOn = true;
		waxing = false;
		notifyAll();
	}

	public synchronized void buffed() {
		waxOn = false;
		buffing = false;
		notifyAll();
	}

	public synchronized void waitForWaxing() throws InterruptedException {
		while (waxOn == false || waxing == true) {
			wait();
		}
		waxing = true;
	}

	public synchronized void waitForBuff() throws InterruptedException {
		while (waxOn == true || buffing == true) {
			wait();
		}
		buffing = true;
	}
}

class WaxOn implements Runnable {
	private Car car;

	public WaxOn(Car c) {
		car = c;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForBuff();
				System.out.println("Wax On! " + this);
				TimeUnit.MILLISECONDS.sleep(50);
				car.waxed();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax on Task");
	}

}

class Waxoff implements Runnable {
	private Car car;

	public Waxoff(Car c) {
		car = c;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				System.out.println("Wax Off! " + this);
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax off Task");
	}
}

class run1 implements Runnable {

	public run1() {
		System.out.println("Start run1 " + this);
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				wait();
			}
			System.out.println("xxxxx");
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("End run1 " + this);
	}

}

class run2 implements Runnable {
	run1 r1;

	public run2(run1 r1) {
		System.out.println("Start run2 " + this);
		this.r1 = r1;
	}

	public void run() {
		try {
			System.out.println("yyyyy");
			TimeUnit.SECONDS.sleep(2);
			synchronized (r1) {
				r1.notifyAll();
			}
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("End run1 " + this);
	}
}

public class WoxOMatic {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService exec = Executors.newCachedThreadPool();
		run1 x = new run1();
		exec.execute(x);
		exec.execute(new run2(x));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}
