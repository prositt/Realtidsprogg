package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class TimeThread extends Thread {

	private ClockInput	input;
	private ClockOutput	output;
	private Semaphore	signal;
	SharedData data;

	int clockRef;
	int hhmmss;
	private int diff;
	private int hh;
	private int mm;
	private int ss;

	int alarm;

	long loopTime;


	TimeThread(SharedData data){
		this.data = data;
	}

	public void run() {
		loopTime = System.currentTimeMillis();
		while(!Thread.currentThread().isInterrupted()){
			loopTime += 1000;
			long delta = loopTime - System.currentTimeMillis();
			if (delta > 0){	
				try {
					sleep(delta);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				data.updateTime();
			}
		}
	}
}
