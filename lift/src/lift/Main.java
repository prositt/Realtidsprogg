package lift;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args){

		LinkedList people;
		LiftData data;
		LiftHandler lift;
		long loopTime;

		people = new LinkedList<Person>();
		data = new LiftData();
		lift = new LiftHandler(data);

		for (int i=0;i<15;i++){
			Person P = new Person(data);
			P.start();
			people.add(P);
		}
		lift.start();
	}
}
