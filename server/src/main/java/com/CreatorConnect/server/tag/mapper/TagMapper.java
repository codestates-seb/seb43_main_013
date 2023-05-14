package com.CreatorConnect.server.tag.mapper;

import com.CreatorConnect.server.freeboard.dto.FreeBoardDto;
import com.CreatorConnect.server.tag.dto.TagDto;
import com.CreatorConnect.server.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

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

    List<Tag> tagPostDtosToTag(List<TagDto.TagInfo> tagInfos);

    // List<Tag> -> TagDto.TagResponse
    List<TagDto.TagResponse> tagsToTagResponseDto(List<Tag> tags);

    // Tag -> TagDto.TagResponse
    @Mapping(source = "tag.tagId", target = "tagId")
    TagDto.TagResponse tagToTagToBoard(Tag tag);
}
