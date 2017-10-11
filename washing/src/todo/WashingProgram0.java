package todo;

import done.AbstractWashingMachine;

public class WashingProgram0 extends WashingProgram {

	public WashingProgram0(AbstractWashingMachine mach,
			double speed,
			TemperatureController tempController,
			WaterController waterController,
			SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	@Override
	protected void wash() throws InterruptedException {

		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_IDLE, 0.0));


		mySpinController.putEvent(new SpinEvent(this,
				SpinEvent.SPIN_OFF));

		myWaterController.putEvent(new WaterEvent(this,
				WaterEvent.WATER_DRAIN, 0.0));

		while (mailbox.tryFetch()!=null);
	}

}
