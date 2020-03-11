package com.rk.utils.executor;

/**
 * 执行回调接口
 * @author ZhaoKang
 *
 * @param <T>
 */
public interface ExecutorCallback<T> {

	/**
	 * 回调函数
	 * @param msg
	 * @param result
	 */
	public void callback(String msg, int result);

	/**
	 * 开始执行回调
	 */
	public void start();

	/**
	 * 获取回调实体参数
	 * @return
	 */
	public T getCallBackInstance();

}
