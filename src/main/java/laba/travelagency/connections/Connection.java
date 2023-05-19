/**
 * 
 */
package laba.travelagency.connections;

import java.time.LocalDateTime;

/**
 * @author sheetal
 *
 */
public class Connection {
	
	private int connectionId;
	private boolean closed;
	
	public Connection(int connectionId) {
		this.connectionId = connectionId;
		this.closed = false;
	}
	
	public synchronized void performQuery() {
		if(closed) {
			throw new IllegalStateException("Connection is closed");
		}
		System.out.println(" ++ [" + LocalDateTime.now() + "] starting operation | thread " + Thread.currentThread().getId() + " | connectionId " + this.connectionId);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" -- [" + LocalDateTime.now() + "] ending operation | thread " + Thread.currentThread().getId() + " | connectionId " + this.connectionId);
	}

	public int getConnectionId() {
		return connectionId;
	}
	
	public boolean isClosed() {
		return closed;
	}

	public synchronized void close() {
		System.out.println("Closing connection : " + getConnectionId());
		this.closed = true;
	}
	
	
	
}
