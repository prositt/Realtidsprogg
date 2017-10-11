package Exe1;
import se.lth.cs.realtime.semaphore.*;
public class PrintOutControlling {





	public static void main(String[] args){
		Semaphore M = new MutexSem();

		Thread one = new MyThread ("ChillHampus",M);
		Thread two = new MyThread ("OchillHampus",M);

		one.start();
		two.start();
	}





}
