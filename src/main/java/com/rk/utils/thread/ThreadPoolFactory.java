package com.rk.utils.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

/**
 * 线程池创建工厂
 * 
 * @author ZhaoKang
 *
 */
public class ThreadPoolFactory {

	@Getter
	private static long keepAliveTime = 30;
	@Getter
	private static int maximumPoolSize = 30;
	@Getter
	private static int corePoolSize = 5;
	private static TimeUnit unit = TimeUnit.SECONDS;

	private static ThreadPoolExecutor pool;

	public static ThreadPoolExecutor getSingleThreadPool(String task) {
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		int maximumPoolSize = 1;
		int corePoolSize = 1;
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		ThreadPoolObserver.monitoring(task, pool);
		return pool;
	}

	public static synchronized ThreadPoolExecutor getGlobalThreadPool() {
		if (pool == null || pool.isShutdown()) {
			BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
			pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		return pool;
	}

	public static synchronized void initPool(long keepAliveTime, int maximumPoolSize, int corePoolSize) {
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolFactory.keepAliveTime = keepAliveTime;
		ThreadPoolFactory.maximumPoolSize = maximumPoolSize;
		ThreadPoolFactory.corePoolSize = corePoolSize;
		if (pool != null && !pool.isShutdown()) {
			pool.shutdown();
		}
		pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
}
