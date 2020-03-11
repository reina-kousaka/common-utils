package com.rk.utils.executor.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rk.utils.executor.ExecuteInstance;
import com.rk.utils.executor.ExecutorCallback;
import lombok.Getter;


/**
 * 命令行执行器
 *
 * @author ZhaoKang
 */
public class CommandExecutor implements CommonExecutor {

    @Getter
    private String cmdId;
    private Process process;
    private String cmd;
    private String[] envp;
    private String dir;
    private ExecutorCallback<?> callback;
    private ExecuteInstance ei;

    public CommandExecutor(String cmdId, String cmd, String[] envp, String dir, ExecutorCallback<?> callback) {
        this.cmdId = cmdId;
        this.cmd = cmd;
        this.envp = envp;
        this.dir = dir;
        this.callback = callback;
    }

    public CommandExecutor(ExecuteInstance ei, ExecutorCallback<?> callback) {
        this.ei = ei;
        this.callback = callback;
        cmdId = ei.getTaskId();
        cmd = ei.getCmd();
        dir = ei.getDir();
        envp = ei.getEnvp();
    }

    public void run() {
        execute();
    }

    public int execute() {
        Runtime runtime = Runtime.getRuntime();
        File dirFile = null;
        int result = 1;
        String msg = "";
        if (dir != null) {
            dirFile = new File(dir);
            if (!dirFile.exists()) {
                System.out.println("#####working dir is not exists : " + dir);
                msg = "working dir is not exists : " + dir;
            } else {
                System.out.println("#####execute cmd: " + cmd);
                try {
                    // runtime.exec(cmd);
                    process = runtime.exec(cmd, envp, dirFile);
                    // CommandExceutorObserver.put(this);
                    result = process.waitFor();
                    System.out.println("#####excute result : " + result);
                    String line;
                    BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((line = is.readLine()) != null) {
                        System.out.println("#####out put:" + line);
                    }
                    is = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    while ((line = is.readLine()) != null) {
                        System.out.println("#####error msg:" + line);
                    }
                    // result = process.exitValue();
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }

            }
        }
        if (callback != null) {
            callback.callback(msg, result);
        }
        return result;

    }

    public static void main(String[] args) {
        ExecutorCallback cb = new ExecutorCallback() {

            public void callback(String cmdId, int result) {
                System.out.println("###" + cmdId + " " + result);

            }

            @Override
            public void start() {

            }

            @Override
            public Object getCallBackInstance() {
                return null;
            }
        };
        CommandExecutor ce = new CommandExecutor("test1", "/cognos/test/test.sh", null, null, cb);
        Thread thread = new Thread(ce);
        thread.start();
    }

    public void destroyCMD() {
        process.destroy();
    }
}
