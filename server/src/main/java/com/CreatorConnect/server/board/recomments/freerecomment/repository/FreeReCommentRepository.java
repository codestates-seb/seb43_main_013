package com.CreatorConnect.server.board.recomments.freerecomment.repository;


import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.freerecomment.entity.FreeReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeReCommentRepository extends JpaRepository<FreeReComment, ReCommentPK> {
}

