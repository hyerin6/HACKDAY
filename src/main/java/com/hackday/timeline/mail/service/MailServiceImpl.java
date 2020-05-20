package com.hackday.timeline.mail.service;

import java.util.ArrayList;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.timeline.mail.domain.Mail;
import com.hackday.timeline.mail.repository.MailRepository;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.domain.MemberAuth;
import com.hackday.timeline.member.repository.MemberRepository;

import lombok.extern.java.Log;

@Log
@Transactional
@Service
public class MailServiceImpl implements MailService {

	private JavaMailSender mailSender;
	private MailRepository mailRepository;
	private MemberRepository memberRepository;

	public MailServiceImpl(JavaMailSender mailSender, MailRepository mailRepository,
		MemberRepository memberRepository) {
		this.mailSender = mailSender;
		this.mailRepository = mailRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public String getKey() throws Exception {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		int num = 0;

		//20글자의 난수 생성
		while (sb.length() < 21) {
			num = ran.nextInt(75) + 48;
			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char)num);
			}
		}

		return sb.toString();
	}

	@Override
	public void mailSendWithMemberKey(String userId) throws Exception, MessagingException {
		String key = getKey();
		Member member = memberRepository.findByUserId(userId).get(0);

		Mail insertMail = new Mail();
		insertMail.setMember(member);
		insertMail.setMailKey(key);

		Mail entityMail = mailRepository.save(insertMail);
		Long mailNo = entityMail.getMailNo();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mail = new MimeMessageHelper(message, true, "UTF-8");
		//SimpleMailMessage mail = new SimpleMailMessage();

		String htmlStr = "<h2>안녕하세요!</h2><br><br>"
			+ "<h3>" + userId + "님</h3>" + "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다 : "
			+ "<a href='http://27.96.134.60:8080" + "/mail/update?mailNo=" + mailNo
			+ "&user_key=" + key + "'>인증하기</a></p>";
		//			mail.setSubject("[본인인증] SNS_TIMELINE님의 인증메일입니다", "utf-8");
		//			mail.setText(htmlStr, "utf-8", "html");
		//			mail.addRecipient(RecipientType.TO, new InternetAddress(userId));
		mail.setTo(userId);

		mail.setFrom("hackdaytimeline@gmail.com");
		mail.setText(htmlStr, true);

		mail.setSubject("[본인인증] SNS_TIMELINE님의 인증메일입니다");
		mailSender.send(message);
	}

	@Override
	public void alterMemberKey(Long mailNo) throws Exception {
		Mail mail = mailRepository.findById(mailNo).orElseThrow(IllegalArgumentException::new);
		log.info(mail.getMember().getUserName());
		mail.setMailKey("Y");
		mailRepository.save(mail);
		Long userNo = mail.getMember().getUserNo();
		Member member = memberRepository.getOne(userNo);
		member.setAuthList(new ArrayList<>());

		MemberAuth auth = new MemberAuth();
		auth.setAuth("ROLE_MEMBER");
		member.addAuth(auth);

		memberRepository.save(member);
	}

}
