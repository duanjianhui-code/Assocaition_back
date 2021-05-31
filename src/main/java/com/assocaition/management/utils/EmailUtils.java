package com.assocaition.management.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author Duanjianhui
 * @date 2021-04-26 12:15 上午
 * @describe
 */
@Configuration
@Component
@Data
@PropertySource({"classpath:config/email/email.properties"})
public class EmailUtils {
    private MimeMessage message;
    private Session session;
    private Transport transport;

    @Value("${mail.smtp.host}")
    private String mailHost;
    @Value("${mail.sender.username}")
    private String sender_username;
    @Value("${mail.sender.password}")
    private String sender_password;
    @Value("${mail.smtp.auth}")
    private String auth;

    private Properties properties = new Properties();

    /*
     * 初始化方法
     */
    public EmailUtils() {}

    /**
     * 发送邮件
     *
     * @param subject
     *            邮件主题
     * @param sendHtml
     *            邮件内容
     * @param attachment
     *            附件
     */
    public void doSendHtmlEmail(String subject, String sendHtml, String[] receiveUsers, File attachment) {
        try {
            // 开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            properties.put("mail.smtp.host",mailHost);
            properties.put("mail.sender.username",sender_username);
            properties.put("mail.sender.password",sender_password);
            properties.put("mail.smtp.auth",auth);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        session = Session.getInstance(properties);
        session.setDebug(false);// 开启后有调试信息
        message = new MimeMessage(session);
        try {
            // 发件人
            InternetAddress from = new InternetAddress(sender_username);
            message.setFrom(from);

            // 邮件主题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件的内容
            if (attachment != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentBodyPart.setDataHandler(new DataHandler(source));

                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

                //MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                multipart.addBodyPart(attachmentBodyPart);
            }

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();

            transport = session.getTransport("smtp");
            // smtp验证
            transport.connect(mailHost, sender_username, sender_password);
            // 发送
            transport.sendMessage(message, getAddress(receiveUsers));

            System.out.println("send success!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**

     * @Title: getAddress
     * @Description: 遍历收件人信息
     * @param emilAddress
     * @return
     * @throws Exception
     * @return: Address[]
     */
    private static Address[] getAddress(String[] emilAddress) throws Exception {
        Address[] address = new Address[emilAddress.length];
        for (int i = 0; i < address.length; i++) {
            address[i] = new InternetAddress(emilAddress[i]);
        }
        return address;
    }


}
