import { useEffect, useState } from "react";
import { type InfiniteData, useQueryClient } from "@tanstack/react-query";
import { useToast } from "@chakra-ui/react";
import moment from "moment";

import { QUERY_KEYS } from "@/hooks/query";

// store
import { useLoadingStore } from "@/store";

// api
import { apiUpdateRecomment } from "@/apis";

// hook
import useResizeTextarea from "@/hooks/useResizeTextarea";

// component
import Avatar from "@/components/Avatar";

// type
import type { ApiFetchCommentsResponse, BoardType, Recomment } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
  commentId: number;
  recomment: Recomment;
}

/** 2023/05/13 - 답글 컴포넌트 - by 1-blue */
const BoardRecomment: React.FC<Props> = ({ type, boardId, commentId, recomment }) => {
  const toast = useToast();
  const { start, end } = useLoadingStore((state) => state);

  /** 2023/05/11 - 답글 수정 textarea resizing - by 1-blue */
  const [textareaRef, handleResizeHeight] = useResizeTextarea();

  /** 2023/05/11 - comment의 content인 textarea 높이 초기화 - by 1-blue */
  useEffect(handleResizeHeight, [handleResizeHeight]);

  /** 2023/05/11 - 답글 수정 여부 - by 1-blue */
  const [disabled, setDisabled] = useState(true);

  /** 2023/05/11 - 답글 내용 - by 1-blue */
  const [content, setContent] = useState(recomment.content);

  /** 2023/05/11 - 답글 추가를 위해 사용 - by 1-blue */
  const queryClient = useQueryClient();

  /** 2023/05/11 - 답글 수정 완료 - by 1-blue */
  const onClickUpdate = async () => {
    if (content.trim().length === 0) {
      textareaRef.current?.focus();

      return toast({
        description: "답글을 입력해주세요!",
        status: "warning",
        duration: 2500,
        isClosable: true,
      });
    }

    try {
      start();

      // TODO: memberId 넣기
      await apiUpdateRecomment(type, { boardId, commentId, recommentId: recomment.recommentId, content, memberId: 1 });

      end();

      queryClient.setQueryData<InfiniteData<ApiFetchCommentsResponse> | undefined>(
        [QUERY_KEYS.comment, type],
        (prev) =>
          prev && {
            ...prev,
            pages: prev.pages.map((page) => ({
              ...page,
              data: page.data.map((comment) => {
                if (comment.commentId !== commentId) return comment;

                return {
                  ...comment,
                  recomments: comment.recomments.map((v) =>
                    v.recommentId !== recomment.recommentId ? v : { ...v, content },
                  ),
                };
              }),
            })),
          },
      );

      return toast({
        description: "답글을 수정했습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });
    } catch (error) {
      console.error(error);

      return toast({
        description: "답글 수정에 실패했습니다. 잠시후에 다시 시도해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    } finally {
      setDisabled(true);
    }
  };

  return (
    <li className="flex space-x-3">
      <Avatar src={recomment.profileImageUrl} className="w-10 h-10 flex-shrink-0" />
      <div className="flex-1 flex flex-col space-y-1">
        <div className="space-x-2">
          <span className="text-xs font-bold">{recomment.nickname}</span>
          <time className="text-xs text-gray-400">{moment(recomment.createdAt).endOf("day").fromNow()}</time>
          {disabled ? (
            <>
              <button
                type="button"
                className="text-xs text-gray-500 hover:font-bold hover:text-gray-600"
                onClick={() => {
                  setDisabled(false);
                  setTimeout(() => textareaRef.current?.focus(), 0);
                }}
              >
                수정
              </button>
              <button
                type="button"
                data-recomment-id={recomment.recommentId}
                className="text-xs text-gray-500 hover:font-bold hover:text-gray-600"
              >
                삭제
              </button>
            </>
          ) : (
            <>
              <button
                type="button"
                className="text-xs text-gray-500 hover:font-bold hover:text-gray-600"
                onClick={() => onClickUpdate()}
              >
                수정 완료
              </button>
              <button
                type="button"
                className="text-xs text-gray-500 hover:font-bold hover:text-gray-600"
                onClick={() => {
                  setDisabled(true);
                  setContent(recomment.content);
                }}
              >
                수정 취소
              </button>
            </>
          )}
        </div>
        <textarea
          className="p-2 leading-4 resize-none overflow-hidden bg-transparent focus:outline-main-500 focus:font-semibold"
          ref={textareaRef}
          disabled={disabled}
          value={content}
          onChange={(e) => {
            setContent(e.target.value);
            handleResizeHeight();
          }}
        />
      </div>
    </li>
  );
};

export default BoardRecomment;
