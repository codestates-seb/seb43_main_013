package com.CreatorConnect.server.helper.event;

import com.CreatorConnect.server.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberRegistrationApplicationEvent {
    private final Member member;
    public MemberRegistrationApplicationEvent(Member member) {
        this.member = member;
    }
}
