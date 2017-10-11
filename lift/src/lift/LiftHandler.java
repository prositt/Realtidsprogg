package lift;

public class LiftHandler extends Thread {

	LiftData data;

	public LiftHandler(LiftData liftData){
		data = liftData;
	}

	public void run(){
		while(true){
			try {
				data.moveLift();
				
				data.drawLevels();
				data.move();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}