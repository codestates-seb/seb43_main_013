package com.CreatorConnect.server.tag.service;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.tag.entity.Tag;
import com.CreatorConnect.server.tag.entity.TagToFeedbackBoard;
import com.CreatorConnect.server.tag.repository.TagRepository;
import com.CreatorConnect.server.tag.repository.TagToFeedbackBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackBoardTagService {
    private final TagRepository tagRepository;
    private final TagToFeedbackBoardRepository tagToFeedbackBoardRepository;

    public FeedbackBoardTagService(TagRepository tagRepository, TagToFeedbackBoardRepository tagToFeedbackBoardRepository) {
        this.tagRepository = tagRepository;
        this.tagToFeedbackBoardRepository = tagToFeedbackBoardRepository;
    }

    /**
     * <태그 등록>
     * 1. 게시글 등록 시 태그 입력 여부 확인
     * 2. 태그가 입력된 경우 해당 태그의 존재 여부 확인
     * 3. 태그 저장
     */
    public List<Tag> createFeedbackBoardTag(List<Tag> tags, FeedbackBoard feedbackBoard) {
        // 1. 게시글 등록 시 태그 입력 여부 확인
        if (tags == null) { // 태그가 없는 경우 null 리턴
            return null;
        }

        // 2. 태그가 입력된 경우 해당 태그의 존재 여부 확인
        for (int i = 0; i < tags.size(); i++) {
            Tag savedTag = findTag(tags.get(i));

            TagToFeedbackBoard tagToFeedbackBoard = new TagToFeedbackBoard(feedbackBoard, savedTag);

            // 3. 태그 저장
            tagToFeedbackBoardRepository.save(tagToFeedbackBoard);
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
    private long findTagId(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
        long tagId = optionalTag.get().getTagId();

        return tagId;
    }

}
