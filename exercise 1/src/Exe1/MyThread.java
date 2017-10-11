package Exe1;

import se.lth.cs.realtime.semaphore.*;

class MyThread extends Thread {
	String theName ;
	Semaphore theSem ;
	public MyThread (
			String n, Semaphore sem ) {
		theName = n;
		theSem = sem ;
	}
	public void run () {
		theSem . take ();
		for ( int t =1; t <=100; t ++) {
			System . out . println (
					theName + ":" + t);
			for ( int y =1;y <=1000000; y++) { }
		}
		theSem . give ();
	}
}
