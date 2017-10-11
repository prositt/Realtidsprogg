package todo;

import done.*;

public class WashingController implements ButtonListener {	
	AbstractWashingMachine theMachine;
	double theSpeed;
	WashingProgram activeProgram;
	TemperatureController temp;
	WaterController water;
	SpinController spin;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		this.theMachine = theMachine;
		this.theSpeed = theSpeed;
		
		temp = new TemperatureController(theMachine, theSpeed);
		water = new WaterController(theMachine, theSpeed);
		spin = new SpinController(theMachine,theSpeed);
		temp.start();
		water.start();
		spin.start();
    }

    public void processButton(int theButton) {
		switch(theButton) {
		case 0: {

			if (activeProgram!= null /*&& !activeProgram.isAlive()*/)activeProgram.interrupt();
			System.out.println("Is proram interupted?");
			if(activeProgram==null){
				System.out.println("  yes, active program is null");
			} else {
				System.out.println("  no, it is not null");
			}
			//activeProgram = new WashingProgram0(theMachine, theSpeed, temp, water, spin);
			//activeProgram.interrupt();
			//activeProgram.start();
			break;
		}
		case 1: {
			if(activeProgram == null || !activeProgram.isAlive()){
				activeProgram = new WashingProgram1(theMachine, theSpeed, temp, water, spin);
				activeProgram.start();
			}
			break;
		}
		case 2: {
			if(activeProgram == null || !activeProgram.isAlive()){
				activeProgram = new WashingProgram2(theMachine, theSpeed, temp, water, spin);
				activeProgram.start();
			}
			break;
		}
		case 3:
			System.out.println("BUTTON: nr 3 pushed");
			if(activeProgram == null || !activeProgram.isAlive()){
				activeProgram = new WashingProgram3(theMachine, theSpeed, temp, water, spin);
				System.out.println("NULL || isInterupted");
				activeProgram.start();
			break;
		}
		default: {
			/* Should not happen */
			break;
		}
		}
    }
}
    
    

