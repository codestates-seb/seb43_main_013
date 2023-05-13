import { FormEventHandler, useEffect, useState } from "react";
import { useToast } from "@chakra-ui/react";
import { type InfiniteData, useQueryClient } from "@tanstack/react-query";

// api
import { apiCreateRecomment } from "@/apis";

import { QUERY_KEYS } from "@/hooks/query";

// store
import { useLoadingStore } from "@/store";

// type
import type { ApiFetchCommentsResponse, BoardType } from "@/types/api";
import useResizeTextarea from "@/hooks/useResizeTextarea";
interface Props {
  type: BoardType;
  boardId: number;
  commentId: number;
}

/** 2023/05/13 - 답글 폼 컴포넌트 - by 1-blue */
const BoardRecommentForm: React.FC<Props> = ({ type, boardId, commentId }) => {
  const toast = useToast();
  const { start, end } = useLoadingStore((state) => state);

  const [content, setContent] = useState("");

  /** 2023/05/13 - textarea 리사이징 - by 1-blue */
  const [textareaRef, handleResizeHeight] = useResizeTextarea();

  /** 2023/05/13 - comment의 content인 textarea 높이 초기화 - by 1-blue */
  useEffect(handleResizeHeight, [handleResizeHeight]);

  /** 2023/05/13 - 답글 추가를 위해 사용 - by 1-blue */
  const queryClient = useQueryClient();

  /** 2023/05/13 - 답글 등록 핸들러 - by 1-blue */
  const onSubmitComment: FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    if (!content.trim().length)
      return toast({
        description: "답글을 입력해주세요!",
        status: "warning",
        duration: 2500,
        isClosable: true,
      });

    try {
      start();

      // TODO: memberId 넣기 commentId
      const { recommentId } = await apiCreateRecomment(type, { boardId, commentId, content, memberId: 1 });

      end();

      // TODO: 멤버 데이터 넣기
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
                  recomments: [
                    ...comment.recomments,
                    {
                      recommentId,
                      content,
                      createdAt: new Date(),
                      modifiedAt: new Date(),
                      email: "",
                      memberId: 1,
                      nickname: "대충 살자",
                      profileImageUrl:
                        "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/7.jpg",
                      recommntCount: 0,
                    },
                  ],
                };
              }),
            })),
          },
      );

      setContent("");

      return toast({
        description: "답글을 등록했습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });
    } catch (error) {
      console.error(error);

      return toast({
        description: "답글 등록에 실패했습니다. 잠시후에 다시 시도해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    }
  };

  return (
    <form className="flex flex-col pt-4" onSubmit={onSubmitComment}>
      <textarea
        ref={textareaRef}
        value={content}
        onChange={(e) => {
          setContent(e.target.value);
          handleResizeHeight();
        }}
        className="resize-none bg-gray-200 w-full min-h-[60px] focus:outline-main-300 rounded-md p-2 focus:bg-gray-100"
      />
      <button
        type="submit"
        className="ml-auto mt-3 px-3 py-2 bg-main-400 text-white font-bold rounded-sm transition-colors hover:bg-main-500 active:bg-main-600"
      >
        답글작성
      </button>
    </form>
  );
};

export default BoardRecommentForm;
