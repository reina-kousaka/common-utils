package com.rk.utils.mail;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.rk.utils.string.StringUtils;
import org.apache.commons.collections.CollectionUtils;

/**
 * MAIL客户端
 *
 * @author ZhaoKang
 */
public class MailClient {

    private static MimeMessage createMimeMessage(Mail mail) {
        try {
            Session session = Session.getDefaultInstance(mail.getConf(), null); // 获得邮件会话对象
            MimeMessage mimeMsg = new MimeMessage(session); // 创建MIME邮件对象

            mimeMsg.setFrom(new InternetAddress(mail.getFrom()));
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            if (mail.getCc() != null) {
                mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getCc()));
            }
            mimeMsg.setSubject(mail.getSubject());
            Multipart mp = createMultipart(mail.getContent());
            if (!CollectionUtils.isEmpty(mail.getFilenames())) {
                addFileAffix(mp, mail.getFilenames(), mail.getFileNameCharset());
            }
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            return mimeMsg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 添加附件
     *
     * @param filename String
     * @param mp
     */
    private static boolean addFileAffix(Multipart mp, Set<String> files, String charset) {

        try {
            if (StringUtils.isEmpty(charset)) {
                charset = "UTF-8";
            }
            BodyPart bp;
            FileDataSource fd;
            for (String file : files) {
                bp = new MimeBodyPart();
                fd = new FileDataSource(file);
                bp.setDataHandler(new DataHandler(fd));
                bp.setFileName(MimeUtility.encodeText(fd.getName(), charset, null));
                mp.addBodyPart(bp);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Multipart createMultipart(String content) {
        BodyPart bp = new MimeBodyPart();
        Multipart mp = new MimeMultipart();
        try {
            bp.setContent(content, "text/html;charset=UTF-8");
            mp.addBodyPart(bp);
            return mp;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean send(Mail mail) {
        try {

            Properties conf = mail.getConf();
            MimeMessage mimeMsg = createMimeMessage(mail);

            String username = mail.getUsername();
            String password = mail.getPassword();
            String smtp = mail.getSmtp();

            Session mailSession = Session.getInstance(conf, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(smtp, username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            if (mimeMsg.getRecipients(Message.RecipientType.CC) != null) {
                transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
            }

            transport.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
