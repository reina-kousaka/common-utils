package com.rk.utils.executor;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 执行实体
 * 
 * @author ZhaoKang
 *
 */
public class ExecuteInstance {

	@Setter
	@Getter
	private String taskId;
	@Setter
	@Getter
	private String taskName;
	@Setter
	@Getter
	private String taskCode;
	@Setter
	@Getter
	private String output;
	@Setter
	@Getter
	private String fixChar;
	@Setter
	@Getter
	private String nullChar;
	@Setter
	@Getter
	private String fileType;
	@Setter
	@Getter
	private String logFile;
	@Setter
	@Getter
	private String param;
	@Setter
	@Getter
	private String cmd;
	@Setter
	@Getter
	private String[] params;
	@Setter
	@Getter
	private String[] types;
	@Setter
	@Getter
	private String dir;
	@Setter
	@Getter
	private String[] envp;

	@Setter
	@Getter
	private int cacheRowNumber;
	@Setter
	@Getter
	private int fetchRonNum;
	@Setter
	@Getter
	private Map<String, String> dataType;
	@Setter
	@Getter
	private String charset;

}
