package com.CreatorConnect.server.board.tag.service;

import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.tag.entity.Tag;
import com.CreatorConnect.server.board.tag.entity.TagToFreeBoard;
import com.CreatorConnect.server.board.tag.repository.TagBoardRepository;
import com.CreatorConnect.server.board.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

            TagToFreeBoard tagBoard = new TagToFreeBoard(freeBoard, savedTag);
            // 3. 태그 저장
            tagBoardRepository.save(tagBoard);
        }
        return tags;
    }

    /**
     * <태그 수정>
     * 1. 태그 수정 시 수정된 태그 존재 여부 확인
     * 2. 수정으로 태그를 입력받지 않은 경우(태그가 없는 상태) 수정 전 게시글에 태그 존재 여부 확인
     *  2-1. 기존 게시글에 태그가 존재하는 경우 게시글에 연결된 태그 삭제
     *
     * 3. 수정된 태그를 입력받은 경우
     *  3-1. 기존 TagBoard에 있는 데이터를 지우고 입력받은 데이터를 TagBoard에 저장
     * // (사용X). 태그가 수정되면서 기존 태그가 사라진 경우 게시글과 태그 연결 해제 (ex 기존 태그 : a,b,c / 수정된 태그 : a,c -> b는 TagToFreeBoard에서 삭제되어야 한다.)
     *  3-2. 수정된 태그가 이미 db에 존재하면 db에 있는 tagId, tagName 사용
     *  3-3. 수정된 태그가 db에 없으면 db에 수정된 태그 저장
     */
    public List<Tag> updateFreeBoardTag(List<Tag> tags, FreeBoard freeBoard) {
        // 1. 태그 수정 시 수정된 태그 존재 여부 확인
        List<TagToFreeBoard> findTagToFreeBoards = tagBoardRepository.findByFreeBoard(freeBoard);
        List<Tag> updatedFreeBoardTags = new ArrayList<>();

        // 2. 수정으로 태그를 입력받지 않은 경우(태그가 없는 상태) 수정 전 게시글에 태그 존재 여부 확인
        if (tags == null) {
            if (!findTagToFreeBoards.isEmpty()) { // 2-1. 기존 게시글에 태그가 존재하는 경우 게시글에 연결된 태그 삭제
                for (int i = 0; i < findTagToFreeBoards.size(); i++) {
                    removeTagToFreeBoard(findTagToFreeBoards.get(i));
                }
                return null;
            }
        }

        // 3. 수정된 태그를 입력받은 경우
        // 3-1. 기존 TagBoard에 있는 데이터를 지우고 입력받은 데이터를 TagBoard에 저장
        for (int i = 0; i < findTagToFreeBoards.size(); i++) {
            removeTagToFreeBoard(findTagToFreeBoards.get(i));
        }



        // 3-1. 태그가 수정되면서 기존 태그가 사라진 경우 게시글과 태그 연결 해제 (ex 기존 태그 : a,b,c / 수정된 태그 : a,c -> b는 TagToFreeBoard에서 삭제되어야 한다.)
//        List<Tag> findTags = findTagToFreeBoards
//                .stream()
//                .map(tagToFreeBoard -> tagToFreeBoard.getTag()).collect(Collectors.toList());
//        List<String> findTagNames = tags
//                .stream()
//                .map(tag -> tag.getTagName()).collect(Collectors.toList());
//
//        for (int i = 0; i < findTags.size(); i++) {
//            if (!findTagNames.contains(findTags.get(i).getTagName())) {
//                removeTagToFreeBoard(new TagToFreeBoard(freeBoard, findTags.get(i)));
//            }
//        }

        for (int i = 0; i < tags.size(); i++) {
            // 3-2. 수정된 태그가 이미 db에 존재하면 db에 있는 tagId, tagName 사용
            Tag updatedTag = findTag(tags.get(i));
            TagToFreeBoard tagToFreeBoard = new TagToFreeBoard(freeBoard, updatedTag);
            updatedFreeBoardTags.add(updatedTag);

            // 3-3. 수정된 태그가 db에 없으면 db에 수정된 태그 저장
            tagBoardRepository.save(tagToFreeBoard);
        }
        return updatedFreeBoardTags;
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

    // TagToFreeBoard 검증 및 저장 메서드
//    private TagToFreeBoard findTagToFreeBoard(TagToFreeBoard tagToFreeBoard) {
//        Optional<TagToFreeBoard> optionalTagToFreeBoard =
//                tagBoardRepository.findByFreeBoard()
//        TagToFreeBoard savedTagToFreeBoard = optionalTagToFreeBoard.orElse(tagBoardRepository.save(tagToFreeBoard));
//        return savedTagToFreeBoard;
//    }

    // TAG_ID 추출 메서드
    public long findTagId(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
        long tagId = optionalTag.get().getTagId();

        return tagId;
    }

    // 게시글과 연결된 태그 삭제 메서드(TagToFreeBoard에 있는 데이터 삭제)
    private void removeTagToFreeBoard(TagToFreeBoard tagToFreeBoard) {
        tagBoardRepository.delete(tagToFreeBoard);
    }

    /**
     * 태그 중복 처리방법
     * 1. 태그가 db에 존재하는지 찾는다
     * 2. 존재하면 해당 태그 이름으로 저장된 id 값을 얻는다
     * 3. 기존 tag 테이블에 있는 태그를 저장하면 기존에 저장된 id값과 태그 이름을 그대로 사용하도록 지정
     */
}
