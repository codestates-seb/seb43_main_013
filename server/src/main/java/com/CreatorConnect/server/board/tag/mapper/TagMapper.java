package com.CreatorConnect.server.board.tag.mapper;

import com.CreatorConnect.server.board.tag.dto.TagDto;
import com.CreatorConnect.server.board.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    // TagDto.Post -> Tag
    default Tag tagPostDtoToTag(TagDto.TagInfo post) {
        if (post.getTagName() == null) { // 태그를 입력하지 않은 경우 null 리턴
            return null;
        }
        Tag tag = new Tag(post.getTagName());
        return tag;
    }

    // List<TagDto.TagInfo> -> List<Tag>
    List<Tag> tagPostDtosToTag(List<TagDto.TagInfo> tagInfos);

    // List<Tag> -> TagDto.TagResponse
    List<TagDto.TagInfo> tagsToTagResponseDto(List<Tag> tags);

    // Tag -> TagDto.TagResponse
    @Mapping(source = "tag.tagId", target = "tagId")
    TagDto.TagInfo tagToTagToBoard(Tag tag);
}
