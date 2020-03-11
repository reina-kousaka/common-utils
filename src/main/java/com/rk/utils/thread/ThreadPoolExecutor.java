package com.rk.utils.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 重载ThreadPoolExecutor，添加自定义处理
 * @author ZhaoKang
 *
 */
public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor {

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		super.execute(command);
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		super.shutdown();
	}
	
	
	

}
