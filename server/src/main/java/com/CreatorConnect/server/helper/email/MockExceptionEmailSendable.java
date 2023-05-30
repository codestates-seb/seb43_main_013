package com.CreatorConnect.server.helper.email;

import org.springframework.mail.MailSendException;

public class MockExceptionEmailSendable implements EmailSendable {
    @Override
    public void send(String[] to, String subject, String message, String templateName) throws InterruptedException {
        Thread.sleep(5000L);
        throw new MailSendException("error while sending Mock email");
    }
}
