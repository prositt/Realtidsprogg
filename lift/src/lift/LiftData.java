package lift;

public class LiftData {

	int here; 
	int next; 
	int [] waitEntry;
	int [] waitExit; 
	int active;
	int load;
	private int dir=-1;
	boolean isMoving=false;
	private static final int MAX_LOAD = 4; //maximum number in lift

	private static final int BOTTOM_FLOOR = 0;
	public static final int TOP_FLOOR = 7;



	LiftView view;

	public LiftData (){
		view = new LiftView();
		here = 0;
		next = 0;
		load = 0;
		waitEntry = new int [TOP_FLOOR + 1];
		waitExit = new int [TOP_FLOOR + 1];
	}

	public synchronized void enterLift(int entryLevel, int exitLevel) throws InterruptedException{
		active++;
		waitEntry[entryLevel]++;
		drawPeople(entryLevel);
		notifyAll();
		while(isMoving || here != entryLevel || load >= MAX_LOAD){
			wait();
		}
		waitEntry[entryLevel]--;
		load++;
		waitExit[exitLevel]++;
		notifyAll();
		while(isMoving || here != exitLevel){
			wait();
		}
		view.drawLift(here,load);
		load--;
		waitExit[exitLevel]--;
		System.out.println("Person out");
		view.drawLift(here, load);
		active--;
		notifyAll();
	}

	public synchronized void moveLift() throws InterruptedException {
		notifyAll();
		while(isMoving || waitExit[here]>0 || (waitEntry[here]>0 && load <MAX_LOAD)){				//vänta på avstingning
			wait();																		//vänta på påstingning
		}
		if(active>0 && !isMoving){																	//om folk på våning
			if(here <= 0 || here >= TOP_FLOOR-1 || isNext()){							//(byta håll)
				dir*=-1;
			}
			isMoving = true;															
			next = here + dir;															
		} else {
			wait();
		}

	}

	public void drawLevels(){
		for (int i=0; i<TOP_FLOOR;i++){
			view.drawLevel(i, waitEntry[i]);
			if(here == i && !isMoving){
				view.drawLift(i, load);
			}
		}
	}

	private boolean isNext(){
		if (dir<0){
			for(int i = here; i>=BOTTOM_FLOOR;i--){
				if(load < MAX_LOAD && waitEntry[i]>0 || waitExit[i]>0){
					return false;
				}
			}
		}else{
			for (int i = here; i<=TOP_FLOOR;i++){
				if(load < MAX_LOAD && waitEntry[i]>0 || waitExit[i]>0){
					return false;
				}
			}
		}
		return true;
	}

	public void move(){
		boolean isMovingCopy;
		int hereCopy, nextCopy, loadCopy;

		synchronized (this) {
			hereCopy = here;
			nextCopy = next;
			loadCopy = load;
			isMovingCopy =isMoving;
		}
		if (isMoving && here!=next){
			view.drawLift(hereCopy, loadCopy);
			view.moveLift(hereCopy, nextCopy);
			synchronized (this){
				here=next;
				isMoving = false;
			}
		}
	}

	public void drawPeople(int i) {
		view.drawLevel(i, waitEntry[i]);

	}
}
