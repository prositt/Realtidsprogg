package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.MutexSem;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ButtonThread extends Thread {

	private ClockInput	in;
	private ClockOutput	out;
	private Semaphore	sig;

	private Semaphore	signal, mutex;

	SharedData data;

	ButtonThread(ClockInput	input,	SharedData data){
		in = input;
		this.data = data;
		signal = input.getSemaphoreInstance();
		//	mutex = data.getMutex();
	}

	@Override
	public void run() {
		int prevChoice = 0;

		while(!Thread.currentThread().isInterrupted()){
			signal.take();

			data.alarmStatus(in.getAlarmFlag());
			
			data.turnSnooze(false);

			int choice = in.getChoice();

			if(prevChoice == ClockInput.SET_ALARM && choice == ClockInput.SHOW_TIME){
				data.setAlarm(in.getValue());
			}

			if(prevChoice == ClockInput.SET_TIME && choice == ClockInput.SHOW_TIME){
				data.setClockTime(in.getValue());
			}
			
			prevChoice = choice;

		}


	}

}




