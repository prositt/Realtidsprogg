package todo;


import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;


public class WaterController extends PeriodicThread {
	AbstractWashingMachine mach;
	int mode;
	double level;
	WashingProgram wp;
	boolean sent;
	// TODO: add suitable attributes

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		mode = WaterEvent.WATER_IDLE;
		level = 0;
		this.mach = mach;
	}

	public void perform() {
		// TODO: implement this method
		WaterEvent e = (WaterEvent) mailbox.tryFetch();
		if (e!=null){
			sent = false;
			mode = e.getMode();
			level = e.getLevel();
			wp = (WashingProgram) e.getSource();
			
		}

		switch(mode){
		case WaterEvent.WATER_IDLE:
			mach.setDrain(false);
			mach.setFill(false);
			break;
			
		case WaterEvent.WATER_FILL:
			mach.setDrain(false);
			if(mach.getWaterLevel()<level){
				mach.setFill(true);
			} else {
				mach.setFill(false);
			}
			if(mach.getWaterLevel()>=level && !sent){
				//System.out.println("Message sent (water), level: "+ level);
				wp.putEvent(new AckEvent(this));
				sent = true;
			}
			break;
			
		case WaterEvent.WATER_DRAIN: 
			mach.setFill(false);
			if(mach.getWaterLevel() > level){
				mach.setDrain(true);
			} else {
				mach.setDrain(false);
			}
			if(mach.getWaterLevel()==level && !sent){
				//System.out.println("Message sent (water), level: "+ level);
				wp.putEvent(new AckEvent(this));
				sent = true;
			}
		break;
		}
	}
}
