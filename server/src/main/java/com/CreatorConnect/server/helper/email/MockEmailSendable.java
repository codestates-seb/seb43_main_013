package com.CreatorConnect.server.helper.email;

public class MockEmailSendable implements EmailSendable {

    @Override
    public void send(String[] to, String subject, String message, String templateName) throws InterruptedException {
        System.out.println("Sent mock email!");
    }
}
