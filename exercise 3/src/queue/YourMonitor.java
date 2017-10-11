package queue;

class YourMonitor {
	private int nCounters;
	private int customerQue[];
	private int clerkQue[];
	private static final int SIZE = 100;
	private int nextQueNbr;
	private int nextClerk;
	
	
	// Put your attributes here...

	YourMonitor(int n) { 
		nCounters = n;
		customerQue = new int[SIZE];
		clerkQue = new int[n];
		nextQueNbr = 0;
	}

	/**
	 * Return the next queue number in the intervall 0...99. 
	 * There is never more than 100 customers waiting.
	 */
	synchronized int customerArrived() {
		customerQue[nextQueNbr] = nextQueNbr;
		if (++nextQueNbr >= SIZE - 1) nextQueNbr = 0;
		return 0;
	}

	/**
	 * Register the clerk at counter id as free. Send a customer if any. 
	 */
	synchronized void clerkFree(int id) { 
		clerkQue[nextClerk] = id;
		if (++nextClerk >= nCounters - 1) nextClerk = 0;
	}

	/**
	 * Wait for there to be a free clerk and a waiting customer, then
	 * return the cueue number of next customer to serve and the counter 
	 * number of the engaged clerk.
	 */
	synchronized DispData getDisplayData() throws InterruptedException { 
		while(customerQue[nextQueNbr]==-1 && clerkQue[nextClerk] ==-1) wait();
		customerQue[nextQueNbr] 
		return null;
	}
}
