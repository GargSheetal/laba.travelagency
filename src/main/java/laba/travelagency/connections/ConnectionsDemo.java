package laba.travelagency.connections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionsDemo {
	
	private static final int THREAD_COUNT = 7;
	private static int POOL_SIZE = 5;
	
	public static void launch() {
		System.out.println("\n *** Launching Connections Demo ***");
		
		System.out.println("\n  -- Creating threads using Runnable -- ");
		Thread thread1 = new Thread(new RunnableDemo());
		Thread thread2 = new Thread(new RunnableDemo());
		
		thread1.start();
		thread2.start();
		
		// Creating Connection Pool with 5 connections
		ConnectionPool connectionPool = ConnectionPool.getInstance(POOL_SIZE);
		
		// Creating Thread Pool with 7 threads
		ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
		
		// creating list of CompletableFuture to store the CompletableFuture instances representing each task.
		System.out.println("\n  -- Working with Connection Pool using CompletableFuture -- ");
		List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // acquire a connection
                    Connection connection = connectionPool.getConnection();

                    int connectionId = connection.getConnectionId();
                    // simulating some operation inside the connection - this performs sleep of 2secs
                    connection.performQuery();

                    // release connection
                    connectionPool.releaseConnection(connection);
                    return "Query Result | thread " + Thread.currentThread().getId() + " | connectionId " + connectionId;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, threadPool);

            futures.add(future);
        }
        
        //using CompletableFuture.allOf to wait for all the CompletableFuture instances in futures to complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		
        // iterating all CompletableFuture in futures to handle their completion
        allFutures.whenComplete((result, error) -> {
        	System.out.println("\n  -- Retrieving the future results when all are completed -- ");
        	for (CompletableFuture<String> future : futures) {
        		if (future.isCompletedExceptionally()) {
        			future.exceptionally(ex -> {  // using exceptionally to handle the exception, If a future completes exceptionally
        				ex.printStackTrace();
        				return null;
        			});
        		} else {
        			try {
        				System.out.println(future.get()); // retrieving the future result and printing it.
        			} catch (InterruptedException | ExecutionException e) {
        				e.printStackTrace();
        			}
        		}
        	}
        	// shutting down the threadPool
        	threadPool.shutdown();
        });
        // blocking the main thread until all the tasks complete.
        allFutures.join();
	}
}
