package com.CreatorConnect.server.freeboard.mapper;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FreeBoardMapper {
    // FreeBoardDto.Post -> FreeBoard
//    @Mapping(source = "memberId",target = "member.memberId")
    default FreeBoard freeBoardPostDtoToFreeBoard(FreeBoardDto.Post post){
        FreeBoard freeBoard = new FreeBoard();

        Member member = new Member();
        member.setMemberId(post.getMemberId());

        Category category = new Category();
        category.setCategory(post.getCategory());

        freeBoard.setMember(member);
        freeBoard.setCategory(category);
        freeBoard.setTitle(post.getTitle());
        freeBoard.setContent(post.getContent());
        return freeBoard;
    }

}
