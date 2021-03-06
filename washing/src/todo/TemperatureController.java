package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class TemperatureController extends PeriodicThread {
	private AbstractWashingMachine mach;
	private int mode;
	private double temp;
	boolean sent;
	WashingProgram wp;
	//boolean isHeating;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		mode = TemperatureEvent.TEMP_IDLE;
		temp = 0;
		this.mach = mach;
		//isHeating = false;
	}

	public void perform() {
		// TODO: implement this method
		TemperatureEvent e = (TemperatureEvent) mailbox.tryFetch();
		if (e!=null){
			mode = e.getMode();
			temp= e.getTemperature();
			sent = false;
			wp = (WashingProgram) e.getSource();
		}
		switch(mode){
		
		case TemperatureEvent.TEMP_IDLE:
			if(mach.getTemperature()<=temp-1.5){
				mach.setHeating(true);
			}else if(mach.getTemperature()>temp-0.5){	
				mach.setHeating(false);
			}
			break;
			
		case TemperatureEvent.TEMP_SET:	
			if(mach.getTemperature()<temp){
			mach.setHeating(true);
			}
			if(!sent && (temp>mach.getTemperature() && mach.getTemperature()>=temp-2)){	
				System.out.println("Message sent (temperature), temp: " + temp);
				wp.putEvent(new AckEvent(this));
				sent = true;
			}
			break;
		}

	}
}
