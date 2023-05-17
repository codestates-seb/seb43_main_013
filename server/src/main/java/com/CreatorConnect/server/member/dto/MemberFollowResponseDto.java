package com.CreatorConnect.server.member.dto;

import com.CreatorConnect.server.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberFollowResponseDto {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;
    private Boolean followed;

}
