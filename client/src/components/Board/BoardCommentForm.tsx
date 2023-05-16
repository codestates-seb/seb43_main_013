import { FormEventHandler, useEffect, useState } from "react";
import { useToast } from "@chakra-ui/react";
import { type InfiniteData, useQueryClient } from "@tanstack/react-query";

import { QUERY_KEYS } from "@/hooks/query";

// api
import { apiCreateComment } from "@/apis";

// hook
import useResizeTextarea from "@/hooks/useResizeTextarea";
import { useMemberStore } from "@/store/useMemberStore";

// store
import { useLoadingStore } from "@/store";

// type
import type { ApiFetchCommentsResponse, BoardType } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
}

/** 2023/05/11 - 댓글 폼 컴포넌트 - by 1-blue */
const BoardCommentForm: React.FC<Props> = ({ type, boardId }) => {
  const toast = useToast();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();

  const [content, setContent] = useState("");

  /** 2023/05/13 - textarea 리사이징 - by 1-blue */
  const [textareaRef, handleResizeHeight] = useResizeTextarea();

  /** 2023/05/11 - comment의 content인 textarea 높이 초기화 - by 1-blue */
  useEffect(handleResizeHeight, [handleResizeHeight]);

  /** 2023/05/11 - 댓글 추가를 위해 사용 - by 1-blue */
  const queryClient = useQueryClient();

  /** 2023/05/11 - 댓글 등록 핸들러 - by 1-blue */
  const onSubmitComment: FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    if (!member) {
      return toast({
        description: "로그인후에 접근해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    }

    if (!content.trim().length)
      return toast({
        description: "댓글을 입력해주세요!",
        status: "warning",
        duration: 2500,
        isClosable: true,
      });

    try {
      loading.start();

      const { commentId } = await apiCreateComment(type, { boardId, content, memberId: member.memberId });

      queryClient.setQueryData<InfiniteData<ApiFetchCommentsResponse> | undefined>(
        [QUERY_KEYS.comment, type],
        (prev) =>
          prev && {
            ...prev,
            pages: prev.pages.map((page) => ({
              ...page,
              data: [
                ...page.data,
                {
                  commentId,
                  content,
                  createdAt: new Date(),
                  modifiedAt: new Date(),
                  email: "",
                  memberId: member.memberId,
                  nickname: member.nickname,
                  profileImageUrl: member.profileImageUrl,
                  recommntCount: 0,
                  recomments: [],
                },
              ],
            })),
          },
      );

      setContent("");

      return toast({
        description: "댓글을 등록했습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });
    } catch (error) {
      console.error(error);

      return toast({
        description: "댓글 등록에 실패했습니다. 잠시후에 다시 시도해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    } finally {
      loading.end();
    }
  };

  return (
    <form className="flex flex-col" onSubmit={onSubmitComment}>
      <textarea
        ref={textareaRef}
        value={content}
        onChange={(e) => {
          setContent(e.target.value);
          handleResizeHeight();
        }}
        className="resize-none bg-sub-200 w-full min-h-[120px] focus:outline-main-300 rounded-md p-2 focus:bg-sub-100"
      />
      <button
        type="submit"
        className="ml-auto mt-3 px-3 py-2 bg-main-400 text-white font-bold rounded-sm transition-colors hover:bg-main-500 active:bg-main-600"
      >
        작성하기
      </button>
    </form>
  );
};

export default BoardCommentForm;
