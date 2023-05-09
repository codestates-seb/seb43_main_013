package com.CreatorConnect.server.member.dto;

import com.CreatorConnect.server.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto extends Auditable {

    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private String phone;
    private boolean oauth;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}