package com.rk.utils.mail;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * MAIL实体
 * 
 * @author ZhaoKang
 *
 */
public class Mail {
	@Getter
	private String from;
	@Getter
	private String to = "";
	@Getter
	private String cc = "";
	@Getter
	@Setter
	private String subject;
	@Getter
	@Setter
	private String content;
	@Getter
	private Set<String> filenames;
	@Getter
	private String smtp;
	@Getter
	private String username;
	@Getter
	private String password;
	@Getter
	private Properties conf;
	@Getter
	private String needAuth;
	@Getter
	private String fileNameCharset;

	public Mail(String smtp, String from, String username, String password, String needAuth) {
		this.from = from;
		this.smtp = smtp;
		this.username = username;
		this.password = password;
		this.needAuth = needAuth;

		conf = new Properties();
		conf.put("mail.smtp.host", smtp); // 设置SMTP主机
		conf.put("mail.smtp.auth", needAuth);
	}

	public Mail(String smtp, String from, String username, String password, String needAuth, String fileNameCharset) {
		this(smtp, from, username, password, needAuth);
		this.fileNameCharset = fileNameCharset;
	}

	public Mail(String smtp, String from, Set<String> tos, Set<String> ccs, String subject, String content,
			String username, String password, String needAuth) {
		this(smtp, from, username, password, needAuth);

		this.subject = subject;
		this.content = content;

		if (tos != null) {
			for (String t : tos) {
				to += "," + t;
			}
		}
		if (ccs != null) {
			for (String c : ccs) {
				cc += "," + c;
			}
		}
	}

	public Mail(String smtp, String from, Set<String> tos, Set<String> ccs, String subject, String content,
			Set<String> filenames, String username, String password, String needAuth, String fileNameCharset) {
		this(smtp, from, tos, ccs, subject, content, username, password, needAuth);

		this.filenames = filenames;
		this.fileNameCharset = fileNameCharset;
	}

	public void addTo(String addr) {
		to += "," + addr;
	}

	public void addFile(String filePath) {
		if (filenames == null) {
			filenames = new HashSet<String>();
		}
		filenames.add(filePath);
	}
}
