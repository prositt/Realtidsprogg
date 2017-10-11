package buffer;

import se.lth.cs.realtime.semaphore.*;

/**
 * The buffer.
 */
class Buffer {
	Semaphore mutex; // For mutual exclusion blocking.
	Semaphore free; // For buffer full blocking.
	Semaphore avail; // For blocking when no data is available.
	String[] buffData; // The actual buffer.
	final int size=8;
	int nextPut;
	int nextGet;
	

	Buffer() {
		buffData = new String[size];
		mutex = new MutexSem();
		free = new CountingSem(8);
		avail = new CountingSem(8);
	}

	void putLine(String input) {
		free.take(); // Wait for buffer empty.      m�jligt full buffer
		mutex.take(); // Wait for exclusive access. en task i taget
		buffData[nextPut] = input; // Store cop  utf�ra tasken�
		if(++nextPut>=8){
			nextPut=0;
		}
		mutex.give(); // Allow others to access.    annan task till�tan att utf�ras
		avail.give(); // Allow others to get line.  k�n �r inte tom
	}

	String getLine() {
		avail.take();								//kolla k� ej tom
		mutex.take();
		//en task i taget
		String ans = buffData[nextGet];	
		buffData[nextGet] = null;//utf�r task
		if(++nextGet>=8){
			nextGet=0;
		}
		mutex.give();								//annan task kan utf�ras
		free.give();								//k�n inte l�ngre full
		return ans;
		
		// Exercise 2 ...
		// Here you should add code so that if the buffer is empty, the
		// calling process is delayed until a line becomes available.
		// A caller of putLine hanging on buffer full should be released.
		// ...
	}
}
