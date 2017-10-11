package todo;

import done.AbstractWashingMachine;

public class WashingProgram1 extends WashingProgram {
	private double speed;

	public WashingProgram1(AbstractWashingMachine mach,
			double speed,
			TemperatureController tempController,
			WaterController waterController,
			SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
		this.speed = speed;
	}

	@Override
	protected void wash() throws InterruptedException {
		System.out.println("START: Program 1");
		
		
		myMachine.setLock(true);								//Lock
		
		myWaterController.putEvent(new WaterEvent(this,			//Fill to 0.5
				WaterEvent.WATER_FILL,
				0.5));
		
		mailbox.doFetch();									//wait for fill
		
		myWaterController.putEvent(new WaterEvent(this,			//turn off fill
				WaterEvent.WATER_IDLE,
				0.5));
		myTempController.putEvent(new TemperatureEvent(this,	//Temp to 60
				TemperatureEvent.TEMP_SET,
				60.0));
		
		mailbox.doFetch();										//wait for temp
		
		myTempController.putEvent(new TemperatureEvent(this,	//Temp on 60
				TemperatureEvent.TEMP_IDLE,
				60.0));
		
		mySpinController.putEvent(new SpinEvent(this,			//Start spin
				SpinEvent.SPIN_SLOW));
		
		System.out.println("Sleep: 1000*60*30/speed "+(speed));
		sleep((long) (1000*60*30/speed));							//Sleep 30 min
		System.out.println("Sleep done");
		
		myTempController.putEvent(new TemperatureEvent(this,	//Turn temp off
				TemperatureEvent.TEMP_IDLE, 0.0));
		
		mySpinController.putEvent(new SpinEvent(this,			//Spin off
				SpinEvent.SPIN_OFF));
		
		myWaterController.putEvent(new WaterEvent(this,			//Drain water
				WaterEvent.WATER_DRAIN, 0.0));
		
		mailbox.doFetch();
		
		for (int i = 0; i<5; i++){								//Rinse 5 times
			myWaterController.putEvent(new WaterEvent(this,
					WaterEvent.WATER_FILL, 0.5));
			mailbox.doFetch();
			mySpinController.putEvent(new SpinEvent(this,
					SpinEvent.SPIN_SLOW));
			sleep((long)(1000*10/speed));
			System.out.println("Sleep done nr: " +i);
			mySpinController.putEvent(new SpinEvent(this,
					SpinEvent.SPIN_OFF));
			myWaterController.putEvent(new WaterEvent(this,
					WaterEvent.WATER_DRAIN, 0.0));
			mailbox.doFetch();
		}
		
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));	//Centrifuge
		System.out.println("Centrifuge sleep initialized");
		sleep((long)(1000*5/speed));
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));		//Spin off
		
		myMachine.setLock(false);								//Unlock
		
		System.out.println("FINISHED: Program 1!");
		this.interrupt();
		
	}

}
