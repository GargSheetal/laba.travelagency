package laba.travelagency.connections;

public class RunnableDemo implements Runnable {

	@Override
	public void run() {
		System.out.println("Runnable is executing Thread : " + Thread.currentThread().getId());
	}

}

