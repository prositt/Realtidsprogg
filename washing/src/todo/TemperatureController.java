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
			if(mach.getTemperature()<=temp-2){
				mach.setHeating(true);
			}else{	
				mach.setHeating(false);
			}
			break;
			
		case TemperatureEvent.TEMP_SET:	
			mach.setHeating(true);
			if(temp>mach.getTemperature() && mach.getTemperature()>=temp-2 && !sent){	
				System.out.println("TEMP EVENT PUT, temp: " + temp+" mode: "+mode);
				wp.putEvent(new AckEvent(this));
				sent = true;
			}
			break;
		}

	}
}
