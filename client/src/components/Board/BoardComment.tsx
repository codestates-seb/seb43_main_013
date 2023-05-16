import { useEffect, useState } from "react";
import { useToast } from "@chakra-ui/react";
import moment from "moment";
import { type InfiniteData, useQueryClient } from "@tanstack/react-query";

import { QUERY_KEYS } from "@/hooks/query";

// api
import { apiUpdateComment } from "@/apis";

// store
import { useLoadingStore } from "@/store";

// hook
import useResizeTextarea from "@/hooks/useResizeTextarea";

// component
import Avatar from "@/components/Avatar";

// type
import type { ApiFetchCommentsResponse, BoardType, Comment } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
  comment: Comment;
}

/** 2023/05/11 - 게시판의 댓글들 컴포넌트 - by 1-blue */
const BoardComment: React.FC<Props> = ({ type, boardId, comment }) => {
  const toast = useToast();
  const { start, end } = useLoadingStore((state) => state);

  /** 2023/05/11 - 답글 더 보기  */
  const [isShow, setIsShow] = useState(false);

  /** 2023/05/11 - 로그인한 유저 정보 - by 1-blue */
  const [textareaRef, handleResizeHeight] = useResizeTextarea();

  /** 2023/05/11 - comment의 content인 textarea 높이 초기화 - by 1-blue */
  useEffect(handleResizeHeight, [handleResizeHeight]);

  /** 2023/05/11 - 댓글 수정 여부 - by 1-blue */
  const [disabled, setDisabled] = useState(true);

  /** 2023/05/11 - 댓글 내용 - by 1-blue */
  const [content, setContent] = useState(comment.content);

  /** 2023/05/11 - 댓글 추가를 위해 사용 - by 1-blue */
  const queryClient = useQueryClient();

  /** 2023/05/11 - 댓글 수정 완료 - by 1-blue */
  const onClickUpdate = async () => {
    if (content.trim().length === 0)
      return toast({
        description: "댓글을 입력해주세요!",
        status: "warning",
        duration: 2500,
        isClosable: true,
      });

    try {
      start();

      // TODO: memberId 넣기
      await apiUpdateComment(type, { boardId, commentId: comment.commentId, content, memberId: 1 });

      end();

      queryClient.setQueryData<InfiniteData<ApiFetchCommentsResponse> | undefined>(
        [QUERY_KEYS.comment, type],
        (prev) =>
          prev && {
            ...prev,
            pages: prev.pages.map((page) => ({
              ...page,
              data: page.data.map((v) => (v.commentId !== comment.commentId ? v : { ...v, content })),
            })),
          },
      );

      return toast({
        description: "댓글을 수정했습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });
    } catch (error) {
      console.error(error);

      return toast({
        description: "댓글 삭제에 실패했습니다. 잠시후에 다시 시도해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    }
  };

  return (
    <li className="flex space-x-3">
      <Avatar src={comment.profileImageUrl} className="w-12 h-12 flex-shrink-0" />
      <div className="flex-1 flex flex-col space-y-2">
        <div className="space-x-2">
          <span className="font-bold">{comment.nickname}</span>
          <time className="text-sm text-sub-400">{moment(comment.createdAt).endOf("day").fromNow()}</time>
          {disabled ? (
            <>
              <button
                type="button"
                className="text-xs text-sub-500 hover:font-bold hover:text-sub-600"
                onClick={() => {
                  setDisabled(false);
                  setTimeout(() => textareaRef.current?.focus(), 0);
                }}
              >
                수정
              </button>
              <button
                type="button"
                data-comment-id={comment.commentId}
                className="text-xs text-sub-500 hover:font-bold hover:text-sub-600"
              >
                삭제
              </button>
            </>
          ) : (
            <>
              <button
                type="button"
                className="text-xs text-sub-500 hover:font-bold hover:text-sub-600"
                onClick={() => {
                  onClickUpdate();
                  setDisabled(true);
                }}
              >
                수정 완료
              </button>
              <button
                type="button"
                className="text-xs text-sub-500 hover:font-bold hover:text-sub-600"
                onClick={() => {
                  setContent(comment.content);
                  setDisabled(true);
                }}
              >
                수정 취소
              </button>
            </>
          )}
        </div>
        <textarea
          className="leading-4 resize-none overflow-hidden bg-transparent"
          ref={textareaRef}
          disabled={disabled}
          value={content}
          onChange={(e) => {
            setContent(e.target.value);
            handleResizeHeight();
          }}
        >
          {comment.content}
        </textarea>

        {!!comment.recomments?.length && (
          <button type="button" className="self-start text-sub-500 text-sm" onClick={() => setIsShow((prev) => !prev)}>
            답글 {comment.recomments?.length}개 {isShow ? "닫기" : "더 보기"}
          </button>
        )}

        {isShow &&
          !!comment.recomments?.length &&
          comment.recomments.map((recomment) => (
            <li key={recomment.recommentId} className="flex space-x-3">
              <Avatar src={recomment.profileImageUrl} className="w-10 h-10 flex-shrink-0" />
              <div className="flex flex-col space-y-1">
                <div className="space-x-2">
                  <span className="text-xs font-bold">{recomment.nickname}</span>
                  <time className="text-xs text-sub-400">{moment(recomment.createdAt).endOf("day").fromNow()}</time>
                </div>
                <p className="text-xs leading-4">{recomment.content}</p>
              </div>
            </li>
          ))}
      </div>
    </li>
  );
};

export default BoardComment;
