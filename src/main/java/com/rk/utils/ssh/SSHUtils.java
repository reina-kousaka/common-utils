package com.rk.utils.ssh;

import com.jcraft.jsch.*;
import com.rk.utils.string.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ssh远程调用
 */
public class SSHUtils {

    /**
     * 远程执行
     * @param cmd 远程命令
     * @param prvKey 本机私钥
     * @param username 用户名（若为空，默认使用端口22）
     * @param host 目标主机
     * @param port 端口
     * @return
     */
    public static Object[] sshExecute(String cmd, String prvKey, String username, String host, int port) {
        JSch ssh = new JSch();
        StringBuffer resp = new StringBuffer();
        Object[] result = new Object[2];
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        try {
            ssh.addIdentity(prvKey);
            Session session = null;
            if (StringUtils.isEmpty(username)) {
                session=ssh.getSession(host);
            } else {
                session = ssh.getSession(username, host, port);
            }
            session.setConfig(config);
            session.connect(3000);
            ChannelExec exe = (ChannelExec) session.openChannel("exec");
            exe.setCommand(cmd);
            exe.setPty(true);
            exe.connect(3000);
            BufferedReader br = new BufferedReader(new InputStreamReader(exe.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                resp.append(line);
            }
            result[0] = exe.getExitStatus();
            result[1] = resp.toString();
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 远程调用，已设置默认私钥、端口、用户
     * @param cmd 远程命令
     * @param host 主机
     * @return
     */
    public static Object[] sshExecute(String cmd, String host,String username,String prvKey) {
        if(StringUtils.isEmpty(prvKey)) {
            prvKey = "~/.ssh/id_rsa";
        }
        int port = 22;
        return sshExecute(cmd, prvKey, username, host, port);
    }
}

