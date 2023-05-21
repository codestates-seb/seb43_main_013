package com.CreatorConnect.server.helper.event;

import com.CreatorConnect.server.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberRegistrationApplicationEvent {
    private final String email;

    public MemberRegistrationApplicationEvent(String email) {
        this.email = email;
    }
}
