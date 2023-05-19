/**
 * 
 */
package laba.travelagency.connections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author sheetal
 *
 */
public class ConnectionPool {
	
	private static ConnectionPool instance;
	private BlockingQueue<Connection> pool;
	
	private ConnectionPool(int POOL_SIZE) {
		// thread-safe
		pool = new LinkedBlockingDeque<>();
	}
	
	private void initializeConnections(int poolSize) {
		for(int i=0; i< poolSize; i++) {
			Connection connection = new Connection(i);
			pool.add(connection);
		}
	}
	
	
	/*
	* Double-Check Locking:
	* To ensure no synchronization overhead for every call to getInstance().
	* Lazy Initialization:
	* Do not instantiate until the first call to getInstance(), i.e. only create when it is actually needed
	*/
	public static ConnectionPool getInstance(int poolSize) {
		if(instance == null) {
			synchronized (ConnectionPool.class) {
				if(instance == null) {
					instance = new ConnectionPool(poolSize);
					instance.initializeConnections(poolSize);
				}
			}
		}
		return instance;
	}
	
	public Connection getConnection() throws InterruptedException {
		Connection connection = pool.take();
		return connection;
	}

	public void releaseConnection(Connection connection) throws InterruptedException {
		pool.put(connection);
	}
	
	public void closeConnections() {
		for (Connection connection : pool) {
			connection.close();
		}
	}
}
