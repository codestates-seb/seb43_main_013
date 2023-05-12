package com.CreatorConnect.server.tag.service;

import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.tag.entity.Tag;
import com.CreatorConnect.server.tag.entity.TagBoard;
import com.CreatorConnect.server.tag.repository.TagBoardRepository;
import com.CreatorConnect.server.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagBoardRepository tagBoardRepository;

    public TagService(TagRepository tagRepository, TagBoardRepository tagBoardRepository) {
        this.tagRepository = tagRepository;
        this.tagBoardRepository = tagBoardRepository;
    }

    /**
     * <태그 등록>
     * 1. 게시글 등록 시 태그 입력 여부 확인
     * 2. 태그가 입력된 경우 해당 태그의 존재 여부 확인
     * 3. 태그 저장
     */
    public List<Tag> createFreeBoardTag(List<Tag> tags, FreeBoard freeBoard) {
        // 1. 게시글 등록 시 태그 입력 여부 확인
        if (tags == null) { // 태그가 없는 경우 null 리턴
            return null;
        }

        for (int i = 0; i < tags.size(); i++) {
            // 2. 태그가 입력된 경우 해당 태그의 존재 여부 확인
            Tag savedTag = findTag(tags.get(i));

            TagBoard tagBoard = new TagBoard(freeBoard, savedTag);
            // 3. 태그 저장
            tagBoardRepository.save(tagBoard);
        }
        return tags;
    }


    // 태그 존재 여부 검증 메서드
    private Tag findTag(Tag tag) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tag.getTagName());
        if (optionalTag.isPresent()) { // 게시글 등록할 때 입력한 태그가 이미 존재하는 경우
            long tagId = findTagId(tag.getTagName()); // 해당 태그의 id 값 추출
            tag.setTagId(tagId); // 기존 tagId 사용
            tag.setTagName(tag.getTagName()); // 기존 tagName 사용
        }
        // db에 태그가 존재하지 않으면 해당 태그 테이블에 저장
        Tag findTag = optionalTag.orElse(tagRepository.save(tag));
        return findTag;
    }

    // TAG_ID 추출 메서드
    public long findTagId(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
        long tagId = optionalTag.get().getTagId();

        return tagId;
    }

    /**
     * 태그 중복 처리방법
     * 1. 태그가 존재하는지 찾는다
     * 2. 존재하면 해당 태그 이름으로 저장된 id 값을 얻는다
     * 3. 기존 tag 테이블에 있는 태그를 저장하면 기존에 저장된 id값과 태그 이름을 그대로 사용하도록 지정
     */
}
