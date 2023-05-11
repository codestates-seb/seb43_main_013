package com.CreatorConnect.server.member.entity;

import com.CreatorConnect.server.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private boolean oauth = false;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 30, nullable = false, updatable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(\\d{3,4})-(\\d{4})$")
    @Column(length = 13, unique = true)
    private String phone;

    @Column
    private String introduction;

    @Column
    private String link;

    @Column
    private String profileImageUrl;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follower> followers = new ArrayList<>();

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<FreeBoard> freeBoards = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<FeedBackBoard> feedbackBoards = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<PromotionBoard> promotionBoards = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<JobBoard> jobBoards = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<ReComment> reComments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Bookmark> bookmarks = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Like> likes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public enum MemberRole {
        ROLE_USER, ROLE_ADMIN
    }

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public enum MemberStatus {
        MEMBER_ACTIVE("활동상태"),
        MEMBER_SLEEP("휴면상태"),
        MEMBER_QUIT("탈퇴상태");

        private final @Getter String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
