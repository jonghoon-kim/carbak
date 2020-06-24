package com.chabak.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public class MailHandler {

    private JavaMailSender mailSender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    public MailHandler(JavaMailSender mailSender) throws MessagingException {
        this.mailSender = mailSender;
        message = this.mailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    // 이메일 타이틀
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    // 이메일 Text
    public void setText(String htmlContent) throws MessagingException {
        messageHelper.setText(htmlContent);
    }

    // 보내는 사람 이메일
    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
        messageHelper.setFrom(email, name);
    }

    // 받는 사람 이메일
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    public void addInline(String contentId, DataSource dataSource) throws  MessagingException {
        messageHelper.addInline(contentId, dataSource);
    }

    public void send() {
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
