package com.CreatorConnect.server.member.follow.entity;

import com.CreatorConnect.server.audit.Auditable;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"to_member", "from_member"}) // 중복 값 방지
)
@IdClass(Follow.PK.class)
public class Follow extends Auditable{

    @Id
    @Column(name = "to_member", insertable = false, updatable = false)
    private Long toMember;

    @Id
    @Column(name = "from_member", insertable = false, updatable = false)
    private Long fromMember;

    @Builder
    public Follow(Long toMember, Long fromMember) {
        this.toMember = toMember;
        this.fromMember = fromMember;
    }

    public static class PK implements Serializable {
        Long toMember;
        Long fromMember;
    }
}
