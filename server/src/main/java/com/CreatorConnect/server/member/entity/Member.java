package com.CreatorConnect.server.member.entity;

import com.CreatorConnect.server.audit.Auditable;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "FOLLOW_FOLLOWING",
            joinColumns = @JoinColumn(name = "FOLLOWING_ID"),
            inverseJoinColumns = @JoinColumn(name = "FOLLOWER_ID"))
    @JsonIgnoreProperties("followings") // Jackson 에서 순환 참조 처리
    private Set<Member> followers = new HashSet<>(); // SET : 중복 방지

    @ManyToMany(mappedBy = "followers")
    @JsonIgnoreProperties("followers")
    private Set<Member> followings = new HashSet<>();

    public void follow (Member member) { // 다른 사람이 나를 팔로우 하는 로직
        followers.add(member);
        member.followings.add(this);
    }

    public void unfollow (Member member) {
        followers.remove(member);
        member.followings.remove(this);
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FeedbackBoard> feedbackBoards = new ArrayList<>();

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
