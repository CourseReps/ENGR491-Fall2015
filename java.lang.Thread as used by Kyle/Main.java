/*
 * Main.java
 * by Kyle Fisher
 * 
 * Shows how to use a thread to get+process information asynchronously
 */
public class Main {
	
	/* a function which does "stuff" with *value*
	 * It is "synchronized" just to be absolutely "thread-safe". */
	private static synchronized void doStuff(int value) {
		System.out.println("Do stuff with value = " + Integer.toString(value));
	}
	
	// a runnable which gets "stuff" then calls *doStuff()*
	private static Runnable getStuffRunnable = new Runnable() {
				@Override
				public synchronized void run() {
					int value = 5; // Getting "stuff" from somewhere. Here, just an integer. 
					doStuff(value); // Process this new information
				}
	};
	
	// instantiate a new thread with the getStuffRunnable and returns this thread
	private static Thread getStuff() {
		Thread thread = new Thread(getStuffRunnable);
		thread.run();
		return thread;
	}
	
	// Run getStuff thread repeatedly. 
	public static void main(String[] args) {
		while (true) {
			getStuff();
			
			// Sleep the parent thread just for demonstration purposes
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Oops! Parent thread couldn't sleep()");
			}
		}
	}

}
