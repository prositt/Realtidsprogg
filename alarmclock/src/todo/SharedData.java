package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {

	private int clockTime;

	int choise;
	int preChoise;

	private ClockInput	input;
	private ClockOutput	output;
	private Semaphore	signal, mutex, disMutex;



	private int alarmTime;
	private int counter;
	private boolean alarmFlag = false;
	private boolean snooze = false;



	SharedData(ClockInput in, ClockOutput out, Semaphore	sig){
		input = in;
		output= out;
		signal= sig;
		clockTime=0;
		mutex = new MutexSem();
	}

	public void updateTime(){
		
		mutex.take();
		output.showTime(editTime());
		mutex.give();
		
		isAlarmReady();	

	}

	public void alarmStatus(boolean alarm){
		alarmFlag = alarm;
	}

	public void setClockTime(int hhmmss){
		mutex.take();
		clockTime = hhmmss;
		mutex.give();
	}

	public void setAlarm(int hhmmss){
		mutex.take();
		alarmTime = hhmmss;
		mutex.give();
	}

	private int editTime(){

		clockTime++;
		int h =clockTime /10000;
		int restminutes = clockTime%10000;
		int m = restminutes/100;
		int s = restminutes%100;
		if (s>59){
			s=0;
			m++;
		}
		if (m>59){
			m=0;
			h++;
		}
		if(h>23){
			h=0;
		}

		return clockTime = h*10000+m*100+s;
	}

	private void isAlarmReady(){
		
		if(alarmFlag && clockTime==alarmTime){
			counter = 20;
			snooze = true;
		}

		if(counter > 0 && snooze){
			output.doAlarm();
			counter--;
			System.out.println("ALARM: " + counter);
		}
	}
	
	void turnSnooze( boolean bool ){
		snooze = bool;
	}





}
