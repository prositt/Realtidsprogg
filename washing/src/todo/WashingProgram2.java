package todo;

import done.AbstractWashingMachine;

public class WashingProgram2 extends WashingProgram {
	private long speed;

	public WashingProgram2(AbstractWashingMachine mach,
			double speed,
			TemperatureController tempController,
			WaterController waterController,
			SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
		this.speed = (long) speed;
	}

	@Override
	protected void wash() throws InterruptedException {

		myMachine.setLock(true);
		
		myWaterController.putEvent(new WaterEvent(this,				// Fill to 0.5
				WaterEvent.WATER_FILL,
				0.5));
		
		mailbox.doFetch();											//Wait for water
		
		myWaterController.putEvent(new WaterEvent(this,				// Stop Fill
				WaterEvent.WATER_IDLE,0.5));
		
		//Prewash
		
		myTempController.putEvent(new TemperatureEvent(this,		//Set temp 40
				TemperatureEvent.TEMP_SET, 40.0));
		
		mailbox.doFetch();											//Wait for temp
		
myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_IDLE, 40));
		
		mySpinController.putEvent(new SpinEvent(this,				//Spin slow
				SpinEvent.SPIN_SLOW));
		
		sleep(1000*60*15/speed);
		
		mySpinController.putEvent(new SpinEvent(this,				//Stop spin
				SpinEvent.SPIN_OFF));
		
		myTempController.putEvent(new TemperatureEvent(this,		//Temp off
				TemperatureEvent.TEMP_IDLE, 0.0));
		
		myWaterController.putEvent(new WaterEvent(this,				//Drain water
				WaterEvent.WATER_DRAIN, 0.0));
		
		mailbox.doFetch();
		
		myWaterController.putEvent(new WaterEvent(this,				//Fill water
				WaterEvent.WATER_FILL, 0.5));
		
		mailbox.doFetch();
		
		myWaterController.putEvent(new WaterEvent(this,				//Stop water
				WaterEvent.WATER_IDLE,
				0.5));
		
		myTempController.putEvent(new TemperatureEvent(this,		//Set temp 60
				TemperatureEvent.TEMP_SET,
				60.0));
		
		mailbox.doFetch();
		
		myTempController.putEvent(new TemperatureEvent(this,		
				TemperatureEvent.TEMP_IDLE,
				60.0));
		
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 60));
		
		mySpinController.putEvent(new SpinEvent(this,				//Spin slow 
				SpinEvent.SPIN_SLOW));
		
		sleep(1000*60*3000/speed);										//Sleep 30
		
		myTempController.putEvent(new TemperatureEvent(this,		//Temp off
				TemperatureEvent.TEMP_IDLE, 0.0));
		
		mySpinController.putEvent(new SpinEvent(this,				//Spin off
				SpinEvent.SPIN_OFF));
		
		myWaterController.putEvent(new WaterEvent(this,				//Water drain
				WaterEvent.WATER_DRAIN, 0.0));
		
		mailbox.doFetch();
		
		for (int i = 0; i<5; i++){									//loop
			myWaterController.putEvent(new WaterEvent(this,			//Water fill
					WaterEvent.WATER_FILL, 0.5));
			mailbox.doFetch();
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			sleep(1000*60*2/speed);										//Sleep 2 min
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			myWaterController.putEvent(new WaterEvent(this,			//Drain water
					WaterEvent.WATER_DRAIN, 0.0));
			mailbox.doFetch();
		}
		
		mySpinController.putEvent(new SpinEvent(this,				//Spin fast
				SpinEvent.SPIN_FAST));
		sleep(1000*60*5/speed);											//Sleep 5 min
		mySpinController.putEvent(new SpinEvent(this,				//Spin off
				SpinEvent.SPIN_OFF));
		
		myMachine.setLock(false);
		this.interrupt();
		
	}

}
