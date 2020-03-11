package com.rk.utils.executor.cmd;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行线程池检查
 * @author ZhaoKang
 *
 */
public class CommandExceutorObserver {

	private static Map<String, CommandExecutor> cmdPool=new HashMap<String, CommandExecutor>();

	public static void put(CommandExecutor ce) {
		cmdPool.put(ce.getCmdId(), ce);

	}

	public static void destroy(String cmdId) {
		CommandExecutor ce = cmdPool.get(cmdId);
		ce.destroyCMD();
	}
}
