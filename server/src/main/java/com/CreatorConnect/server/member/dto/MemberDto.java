package com.CreatorConnect.server.member.dto;

import com.CreatorConnect.server.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        private String email;

        @NotNull
        private String password;

        @NotBlank(message = "이름은 필수 입력사항입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "이름은 한글 또는 영어로만 입력해주세요.")
        private String name;

        @NotBlank(message = "닉네임은 필수 입력사항입니다.")
        private String nickname;

        @Pattern(regexp = "^01(?:0|1|[6-9])-(\\d{3,4})-(\\d{4})$")
        private String phone;

        private String introduction;

        private String link;

        private String profileImageUrl;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CheckPassword {
        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch extends Auditable {

        private Long memberId;

        private String password;

        private String nickname;

        @Pattern(regexp = "^01(?:0|1|[6-9])-(\\d{3,4})-(\\d{4})$")
        private String phone;

        private String introduction;

        private String link;

        private String profileImageUrl;

    }

}
