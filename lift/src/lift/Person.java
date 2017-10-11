package lift;

public class Person extends Thread {

	private int entryLevel;
	private int exitLevel;
	private LiftData data;
	
	long loopTime;

	public Person(LiftData liftData){
		data = liftData;
	}

	@Override
	public void run() {
		
		entryLevel=(int) (Math.random()*data.TOP_FLOOR);
		int exitLevel;
		do{
				exitLevel = (int) ((Math.random()*6));
		}while(exitLevel==entryLevel);
		
		
		loopTime = System.currentTimeMillis();
		while(!Thread.currentThread().isInterrupted()){
			loopTime += Math.random()*4500;
			
			long delta = loopTime - System.currentTimeMillis();
			if (delta > 0){	
				try {
					sleep(delta);
					data.enterLift(entryLevel,exitLevel);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}








}
