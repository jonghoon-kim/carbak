package com.chabak.services;

import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;

    private final JavaMailSender javaMailSender;

    public int insert(Member member){
        return memberDao.insertMember(member);
    }

    public Member idCheck(String id) throws Exception {
        return memberDao.idCheck(id);
    }

    /* 이메일 중복 확인*/
    public Member emailCheck(String email) throws Exception {
        return memberDao.emailCheck(email);
    }

    /* 이메일 코드 보내기*/
    public MemberService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public boolean send(String subject, String text, String from, String to){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom(from);
            helper.setTo(to);

            javaMailSender.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
