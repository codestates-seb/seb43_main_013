package com.CreatorConnect.server.helper.event;

import com.CreatorConnect.server.helper.email.EmailSender;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Configuration
@Component
@Slf4j
public class MemberRegistrationEventListener {
    @Value("${mail.subject.member.registration}")
    private String subject;

    @Value("${mail.template.name.member.join}")
    private String templateName;

    private final EmailSender emailSender;
    private final MemberService memberService;

    public MemberRegistrationEventListener(EmailSender emailSender, MemberService memberService) {
        this.emailSender = emailSender;
        this.memberService = memberService;
    }

    @Async
    @EventListener
    public void listen(MemberRegistrationApplicationEvent event) throws Exception {
        try {
            String[] to = new String[]{event.getMember().getEmail()};
            String message = event.getMember().getEmail() + "님, 회원 가입이 성공적으로 완료되었습니다.";
            emailSender.sendEmail(to, subject, message, templateName);
        } catch (MailSendException e) {
            e.printStackTrace();
            log.error("MailSendException: rollback for Member Registration:");
            Member member = event.getMember();
            memberService.deleteMember(member.getMemberId());
        }
    }
}
