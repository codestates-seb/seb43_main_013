package com.CreatorConnect.server.member.controller;

import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.board.jobboard.entity.JobBoard;
import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.member.bookmark.entity.Bookmark;
import com.CreatorConnect.server.member.dto.MemberBoardResponseDto;
import com.CreatorConnect.server.member.dto.MemberDto;
import com.CreatorConnect.server.member.dto.MemberFollowResponseDto;
import com.CreatorConnect.server.member.dto.MemberResponseDto;
import com.CreatorConnect.server.member.entity.Member;
import com.CreatorConnect.server.member.like.entity.Like;
import com.CreatorConnect.server.member.mapper.MemberMapper;
import com.CreatorConnect.server.member.repository.MemberRepository;
import com.CreatorConnect.server.member.service.MemberService;
import com.CreatorConnect.server.response.MultiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Validated
@RestController
public class MemberController {

    private static final String MEMBER_DEFAULT_URL = "/api/member";
    private static final String MEMBER_ALL_MAPPING_URL = "/api/members";

    private final MemberMapper mapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberController(MemberMapper mapper, MemberService memberService, MemberRepository memberRepository) {
        this.mapper = mapper;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/")
    public ResponseEntity home(){return new ResponseEntity(HttpStatus.OK);}

    @PostMapping("/api/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberDtoPost) {

        Member member = mapper.memberPostDtoToMember(memberDtoPost);
        Member createdMember = memberService.createMember(member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(MEMBER_DEFAULT_URL + "/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive Long memberId,
                                      @Valid @RequestBody MemberDto.Patch memberDtoPatch,
                                      @RequestHeader(value = "Authorization") String authorizationToken) {

        memberDtoPatch.setMemberId(memberId);
        Member member = mapper.memberPatchDtoToMember(memberDtoPatch);
        Member updateMember = memberService.updateMember(memberId, member);

        MemberResponseDto responseDto = mapper.memberToMemberResponseDto(updateMember);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(MEMBER_DEFAULT_URL + "/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive Long memberId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final Member loginUser;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            loginUser = memberService.findVerifiedMember(authentication.getName());
        } else {
            loginUser = null;
        }

        Member findMember = memberService.findMember(memberId);
        if (findMember == null) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        MemberResponseDto responseDto = mapper.memberToMemberResponseDto(findMember);

        if (loginUser != null) {
            if (loginUser.getFollowings().stream().anyMatch(member -> member.equals(findMember))) {
                responseDto.setFollowed(true);
            }

            if (loginUser.getMemberId().equals(memberId)) {
                responseDto.setMyPage(true);
            }
        }

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(MEMBER_ALL_MAPPING_URL)
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size) {

        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();

        List<MemberResponseDto> responseDtos = mapper.membersToMemberResponseDtos(members);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping(MEMBER_DEFAULT_URL + "/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive Long memberId,
                                       @RequestHeader(value = "Authorization") String authorizationToken) {

        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/member/{member-id}/password")
    public ResponseEntity checkPassword(@PathVariable("member-id") @Positive Long memberId,
                                        @Valid @RequestBody MemberDto.CheckPassword checkPasswordDto,
                                        @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);

        boolean checkPassword =
                memberService.checkPassword(token, memberId, checkPasswordDto.getPassword());

        if (checkPassword) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/api/member/{member-id}/follow")
    public ResponseEntity followMember(@PathVariable("member-id") @Positive Long memberId,
                                       @RequestHeader(value = "Authorization") String authorizationToken) {

        Member loginUser = memberService.getLoggedinMember();
        Member memberToFollow = memberService.findVerifiedMember(memberId);

        if (loginUser.getMemberId() == memberToFollow.getMemberId()){
            return new ResponseEntity(
                    new BusinessLogicException(ExceptionCode.INVALID_MEMBER), HttpStatus.CONFLICT);
        }

        // 현재 로그인한 사용자가 이미 해당 사용자를 팔로우하고 있는지 확인
        boolean isAlreadyFollowing = loginUser.getFollowings().contains(memberToFollow);

        // 현재 로그인한 사용자가 해당 사용자를 팔로우하지 않았다면 팔로우
        if (!isAlreadyFollowing) {
            memberToFollow.follow(loginUser);
            memberRepository.save(memberToFollow);
            memberRepository.save(loginUser);

            return new ResponseEntity<>(HttpStatus.OK);

        } else return new ResponseEntity(
                new BusinessLogicException(ExceptionCode.FOLLOWING_ALREADY_EXISTS), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/api/member/{member-id}/follow")
    public ResponseEntity unfollowMember(@PathVariable("member-id") @Positive Long memberId,
                                         @RequestHeader(value = "Authorization") String authorizationToken) {

        Member loginUser = memberService.getLoggedinMember();
        Member memberToUnFollow = memberService.findVerifiedMember(memberId);

        // 현재 로그인한 사용자가 이미 해당 사용자를 팔로우하고 있는지 확인
        boolean isAlreadyFollowing = loginUser.getFollowings().contains(memberToUnFollow);

        if (isAlreadyFollowing){
            memberToUnFollow.unfollow(loginUser);
            memberRepository.save(memberToUnFollow);
            memberRepository.save(loginUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else return new ResponseEntity(
                new BusinessLogicException(ExceptionCode.FOLLOWING_ALREADY_DELETED), HttpStatus.CONFLICT);
    }

    @GetMapping("/api/member/{member-id}/followings")
    public ResponseEntity getFollowings(@PathVariable("member-id") @Positive Long memberId,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {

        // 현재 로그인한 사용자 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Member loginUser;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            loginUser = memberService.findVerifiedMember(authentication.getName());
        } else {
            loginUser = null;
        }

        // 조회 할 사용자 정보
        Member member = memberService.findVerifiedMember(memberId);
        Set<Member> followings = member.getFollowings();
        int totalElements = followings.size();

        List<MemberFollowResponseDto> response = followings.stream()
                .map(following -> {
                    boolean isFollowing = loginUser != null && loginUser.getFollowings().contains(following);
                    return new MemberFollowResponseDto(
                            following.getMemberId(),
                            following.getNickname(),
                            following.getProfileImageUrl(),
                            isFollowing // 팔로우 여부 설정
                    );
                })
                .skip((page - 1) * size) // 페이지네이션 처리
                .limit(size)
                .collect(Collectors.toList());

        Page<MemberFollowResponseDto> pageResponse =
                new PageImpl<>(response, PageRequest.of(page - 1, size), totalElements);

        return new ResponseEntity(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/api/member/{member-id}/followers")
    public ResponseEntity getFollowers(@PathVariable("member-id") @Positive Long memberId,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size) {

        // 현재 로그인한 사용자 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Member loginUser;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            loginUser = memberService.findVerifiedMember(authentication.getName());
        } else {
            loginUser = null;
        }

        // 조회할 사용자 정보
        Member member = memberService.findVerifiedMember(memberId);
        Set<Member> followers = member.getFollowers();
        int totalElements = followers.size();

        List<MemberFollowResponseDto> response = followers.stream()
                .map(follower -> {
                    boolean isFollowing = loginUser != null && loginUser.getFollowings().contains(follower);
                    return new MemberFollowResponseDto(
                            follower.getMemberId(),
                            follower.getNickname(),
                            follower.getProfileImageUrl(),
                            isFollowing // 팔로우 여부 설정
                    );
                })
                .skip((page - 1) * size) // 페이지네이션 처리
                .limit(size)
                .collect(Collectors.toList());

        Page<MemberFollowResponseDto> pageResponse =
                new PageImpl<>(response, PageRequest.of(page - 1, size), totalElements);

        return new ResponseEntity(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/api/member/{member-id}/liked")
    public ResponseEntity getliked(@PathVariable("member-id") @Positive Long memberId,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {

        Member member = memberService.findVerifiedMember(memberId);

        Set<Like> liked = member.getLikes();
        int totalElements = liked.size();

        List<MemberBoardResponseDto> response = liked.stream()
                .map(like -> {
                    if (like.getBoardType() == Like.BoardType.FREEBOARD) {
                        return new MemberBoardResponseDto(
                                like.getBoardType().toString(),
                                like.getFreeBoard().getFreeBoardId(),
                                like.getFreeBoard().getTitle(),
                                like.getFreeBoard().getContent(),
                                like.getFreeBoard().getCommentCount(),
                                like.getFreeBoard().getLikeCount(),
                                like.getFreeBoard().getViewCount(),
                                like.getFreeBoard().getCategoryName(),
                                like.getFreeBoard().getMember().getMemberId(),
                                like.getFreeBoard().getMember().getEmail(),
                                like.getFreeBoard().getMember().getNickname(),
                                like.getFreeBoard().getMember().getProfileImageUrl(),
                                like.getFreeBoard().getCreatedAt(),
                                like.getFreeBoard().getModifiedAt()
                        );
                    } else if (like.getBoardType() == Like.BoardType.FEEDBACKBOARD) {
                        return new MemberBoardResponseDto(
                                like.getBoardType().toString(),
                                like.getFeedbackBoard().getFeedbackBoardId(),
                                like.getFeedbackBoard().getTitle(),
                                like.getFeedbackBoard().getContent(),
                                like.getFeedbackBoard().getCommentCount(),
                                like.getFeedbackBoard().getLikeCount(),
                                like.getFeedbackBoard().getViewCount(),
                                like.getFeedbackBoard().getFeedbackCategoryName(),
                                like.getFeedbackBoard().getMember().getMemberId(),
                                like.getFeedbackBoard().getMember().getEmail(),
                                like.getFeedbackBoard().getMember().getNickname(),
                                like.getFeedbackBoard().getMember().getProfileImageUrl(),
                                like.getFeedbackBoard().getCreatedAt(),
                                like.getFeedbackBoard().getModifiedAt()
                        );
                    } else if (like.getBoardType() == Like.BoardType.JOBBOARD) {
                        return new MemberBoardResponseDto(
                                like.getBoardType().toString(),
                                like.getJobBoard().getJobBoardId(),
                                like.getJobBoard().getTitle(),
                                like.getJobBoard().getContent(),
                                like.getJobBoard().getCommentCount(),
                                like.getJobBoard().getLikeCount(),
                                like.getJobBoard().getViewCount(),
                                like.getJobBoard().getJobCategoryName(),
                                like.getJobBoard().getMember().getMemberId(),
                                like.getJobBoard().getMember().getEmail(),
                                like.getJobBoard().getMember().getNickname(),
                                like.getJobBoard().getMember().getProfileImageUrl(),
                                like.getJobBoard().getCreatedAt(),
                                like.getJobBoard().getModifiedAt()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .skip((page - 1) * size) // 페이지네이션 처리
                .limit(size)
                .collect(Collectors.toList());

        Page<MemberBoardResponseDto> pageResponse =
                new PageImpl<>(response, PageRequest.of(page - 1, size), totalElements);

        return new ResponseEntity( new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/api/member/{member-id}/bookmarked")
    public ResponseEntity getbookmarked(@PathVariable("member-id") @Positive Long memberId,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {

        Member member = memberService.findVerifiedMember(memberId);

        Set<Bookmark> bookmarked = member.getBookmarks();
        int totalElements = bookmarked.size();

        List<MemberBoardResponseDto> response = bookmarked.stream()
                .map(bookmark -> {
                    if (bookmark.getBoardType() == Bookmark.BoardType.FREEBOARD) {
                        return new MemberBoardResponseDto(
                                bookmark.getBoardType().toString(),
                                bookmark.getFreeBoard().getFreeBoardId(),
                                bookmark.getFreeBoard().getTitle(),
                                bookmark.getFreeBoard().getContent(),
                                bookmark.getFreeBoard().getCommentCount(),
                                bookmark.getFreeBoard().getLikeCount(),
                                bookmark.getFreeBoard().getViewCount(),
                                bookmark.getFreeBoard().getCategoryName(),
                                bookmark.getFreeBoard().getMember().getMemberId(),
                                bookmark.getFreeBoard().getMember().getEmail(),
                                bookmark.getFreeBoard().getMember().getNickname(),
                                bookmark.getFreeBoard().getMember().getProfileImageUrl(),
                                bookmark.getFreeBoard().getCreatedAt(),
                                bookmark.getFreeBoard().getModifiedAt()
                        );
                    } else if (bookmark.getBoardType() == Bookmark.BoardType.FEEDBACKBOARD) {
                        return new MemberBoardResponseDto(
                                bookmark.getBoardType().toString(),
                                bookmark.getFeedbackBoard().getFeedbackBoardId(),
                                bookmark.getFeedbackBoard().getTitle(),
                                bookmark.getFeedbackBoard().getContent(),
                                bookmark.getFeedbackBoard().getCommentCount(),
                                bookmark.getFeedbackBoard().getLikeCount(),
                                bookmark.getFeedbackBoard().getViewCount(),
                                bookmark.getFeedbackBoard().getFeedbackCategoryName(),
                                bookmark.getFeedbackBoard().getMember().getMemberId(),
                                bookmark.getFeedbackBoard().getMember().getEmail(),
                                bookmark.getFeedbackBoard().getMember().getNickname(),
                                bookmark.getFeedbackBoard().getMember().getProfileImageUrl(),
                                bookmark.getFeedbackBoard().getCreatedAt(),
                                bookmark.getFeedbackBoard().getModifiedAt()
                        );
                    } else if (bookmark.getBoardType() == Bookmark.BoardType.JOBBOARD) {
                        return new MemberBoardResponseDto(
                                bookmark.getBoardType().toString(),
                                bookmark.getJobBoard().getJobBoardId(),
                                bookmark.getJobBoard().getTitle(),
                                bookmark.getJobBoard().getContent(),
                                bookmark.getJobBoard().getCommentCount(),
                                bookmark.getJobBoard().getLikeCount(),
                                bookmark.getJobBoard().getViewCount(),
                                bookmark.getJobBoard().getJobCategoryName(),
                                bookmark.getJobBoard().getMember().getMemberId(),
                                bookmark.getJobBoard().getMember().getEmail(),
                                bookmark.getJobBoard().getMember().getNickname(),
                                bookmark.getJobBoard().getMember().getProfileImageUrl(),
                                bookmark.getJobBoard().getCreatedAt(),
                                bookmark.getJobBoard().getModifiedAt()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .skip((page - 1) * size) // 페이지네이션 처리
                .limit(size)
                .collect(Collectors.toList());

        Page<MemberBoardResponseDto> pageResponse =
                new PageImpl<>(response, PageRequest.of(page - 1, size), totalElements);

        return new ResponseEntity( new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

    @GetMapping("/api/member/{member-id}/written")
    public ResponseEntity getwritten(@PathVariable("member-id") @Positive Long memberId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {

        Member member = memberService.findVerifiedMember(memberId);

        List<FreeBoard> freeBoards = member.getFreeBoards();
        List<FeedbackBoard> feedbackBoards = member.getFeedbackBoards();
        List<JobBoard> jobBoards = member.getJobBoards();

        List<MemberBoardResponseDto> response = Stream.concat(
                        Stream.concat(
                                freeBoards.stream().map(freeBoard -> new MemberBoardResponseDto(
                                        "FREEBOARD",
                                        freeBoard.getFreeBoardId(),
                                        freeBoard.getTitle(),
                                        freeBoard.getContent(),
                                        freeBoard.getCommentCount(),
                                        freeBoard.getLikeCount(),
                                        freeBoard.getViewCount(),
                                        freeBoard.getCategoryName(),
                                        freeBoard.getMember().getMemberId(),
                                        freeBoard.getMember().getEmail(),
                                        freeBoard.getMember().getNickname(),
                                        freeBoard.getMember().getProfileImageUrl(),
                                        freeBoard.getCreatedAt(),
                                        freeBoard.getModifiedAt()
                                )),
                                feedbackBoards.stream().map(feedbackBoard -> new MemberBoardResponseDto(
                                        "FEEDBACKBOARD",
                                        feedbackBoard.getFeedbackBoardId(),
                                        feedbackBoard.getTitle(),
                                        feedbackBoard.getContent(),
                                        feedbackBoard.getCommentCount(),
                                        feedbackBoard.getLikeCount(),
                                        feedbackBoard.getViewCount(),
                                        feedbackBoard.getFeedbackCategoryName(),
                                        feedbackBoard.getMember().getMemberId(),
                                        feedbackBoard.getMember().getEmail(),
                                        feedbackBoard.getMember().getNickname(),
                                        feedbackBoard.getMember().getProfileImageUrl(),
                                        feedbackBoard.getCreatedAt(),
                                        feedbackBoard.getModifiedAt()
                                ))
                        ),
                        jobBoards.stream().map(jobBoard -> new MemberBoardResponseDto(
                                "JOBBOARD",
                                jobBoard.getJobBoardId(),
                                jobBoard.getTitle(),
                                jobBoard.getContent(),
                                jobBoard.getCommentCount(),
                                jobBoard.getLikeCount(),
                                jobBoard.getViewCount(),
                                jobBoard.getJobCategoryName(),
                                jobBoard.getMember().getMemberId(),
                                jobBoard.getMember().getEmail(),
                                jobBoard.getMember().getNickname(),
                                jobBoard.getMember().getProfileImageUrl(),
                                jobBoard.getCreatedAt(),
                                jobBoard.getModifiedAt()
                        ))
                )
                .sorted(Comparator.comparing(MemberBoardResponseDto::getCreatedAt).reversed())
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());

        int totalElements = response.size();

        Page<MemberBoardResponseDto> pageResponse = new PageImpl<>(response,
                PageRequest.of(page - 1, size), totalElements);

        return new ResponseEntity<>(new MultiResponseDto<>(pageResponse.getContent(), pageResponse), HttpStatus.OK);
    }

}