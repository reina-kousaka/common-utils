package com.rk.utils.thread;

import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 线程池观察器
 * 
 * @author ZhaoKang
 *
 */
public class ThreadPoolObserver {
	private static Logger log = LoggerFactory.getLogger(ThreadPoolObserver.class);
	private static Map<String, Integer> failedThreadCounter = new Hashtable<String, Integer>();

	private static Map<String, ThreadPoolExecutor> singtonPools = new Hashtable<String, ThreadPoolExecutor>();

	public static void checkPools() {
		ThreadPoolExecutor pool;
		log.info("Singleton Pool size:" + singtonPools.keySet().size());
		for (String task : singtonPools.keySet()) {
			pool = singtonPools.get(task);
			info(task, pool);
			if (pool == null) {
			} else {
				if (pool.isTerminated()) {
					log.info("is shutdown");
					singtonPools.remove(task);
					failedThreadCounter.remove(task);
				}
			}
		}

		ThreadPoolExecutor globalPool = ThreadPoolFactory.getGlobalThreadPool();
		if (globalPool.getCorePoolSize() * 2 <= globalPool.getMaximumPoolSize()
				&& globalPool.getActiveCount() == globalPool.getCorePoolSize()
				&& globalPool.getQueue().size() > globalPool.getCompletedTaskCount()) {
			globalPool.setCorePoolSize(globalPool.getCorePoolSize() * 2);
		}
		info("Global Thread Pool", ThreadPoolFactory.getGlobalThreadPool());
	}

	public static void monitoring(String task, ThreadPoolExecutor pool) {
		ThreadPoolExecutor lastPool = singtonPools.get(task);
		if (lastPool == null || lastPool.isTerminated()) {
			singtonPools.put(task, pool);
			// failedThreadCounter.put(task, 0);
		}
	}

	private static void info(String task, ThreadPoolExecutor pool) {
		if (pool == null) {
			log.info("task pool:" + task + ", has bean destroyed");
		} else {
			log.info("task pool:" + task);
			long taskNum = pool.getTaskCount();
			long completedNum = pool.getCompletedTaskCount();
			int runningNUm = pool.getActiveCount();
			log.info("taskNum:" + taskNum);
			log.info("completedNum:" + completedNum);
			// log.info("filedNum:" + failedThreadCounter.get(task));
			log.info("runningNUm:" + runningNUm);
		}
	}

	public static void logFailed(String task) {
		Integer num = failedThreadCounter.get(task);
		if (num == null) {
			log.warn("task:" + task + ",no failed counter founded");
		} else {
			failedThreadCounter.put(task, num + 1);
		}
	}
}
