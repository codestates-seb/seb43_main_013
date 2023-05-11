package com.CreatorConnect.server.tag.mapper;

import com.CreatorConnect.server.tag.dto.TagDto;
import com.CreatorConnect.server.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    // TagDto.Post -> Tag
    default Tag tagPostDtoToTag(TagDto.Post post) {
        if (post == null) { // 태그를 입력하지 않은 경우 null 리턴
            return null;
        }
        Tag tag = new Tag(post.getTagName());
        return tag;
    }

    List<Tag> tagPostDtosToTag(List<TagDto.Post> posts);
}