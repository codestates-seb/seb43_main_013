import { FormEventHandler, useEffect, useRef, useState } from "react";
import { type InfiniteData, useQueryClient } from "@tanstack/react-query";

// api
import { apiCreateRecomment } from "@/apis";

import { QUERY_KEYS } from "@/hooks/query";

// hook
import useResizeTextarea from "@/hooks/useResizeTextarea";
import { useMemberStore } from "@/store/useMemberStore";
import useCustomToast from "@/hooks/useCustomToast";

// store
import { useLoadingStore } from "@/store";

// type
import type { ApiFetchCommentsResponse, BoardType } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
  commentId: number;
  onShowRecomment: () => void;
}

/** 2023/05/13 - 답글 폼 컴포넌트 - by 1-blue */
const BoardRecommentForm: React.FC<Props> = ({ type, boardId, commentId, onShowRecomment }) => {
  const toast = useCustomToast();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();

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

    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    if (!content.trim().length) return toast({ title: "답글을 입력해주세요!", status: "warning" });

    try {
      loading.start();

      const { recommentId } = await apiCreateRecomment(type, {
        boardId,
        commentId,
        content,
        memberId: member.memberId,
      });

      queryClient.setQueryData<InfiniteData<ApiFetchCommentsResponse> | undefined>(
        [QUERY_KEYS.comment, type, boardId],
        (prev) =>
          prev && {
            ...prev,
            pages: prev.pages.map((page) => ({
              ...page,
              data: page.data.map((comment) => {
                console.log(comment.commentId, commentId);

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
                      email: member.email,
                      memberId: member.memberId,
                      nickname: member.nickname,
                      profileImageUrl: member.profileImageUrl,
                      recommntCount: 0,
                    },
                  ],
                };
              }),
            })),
          },
      );

      setContent("");
      onShowRecomment();

      return toast({ title: "답글을 등록했습니다.", status: "success" });
    } catch (error) {
      console.error(error);

      return toast({ title: "답글 등록에 실패했습니다. 잠시후에 다시 시도해주세요!", status: "error" });
    } finally {
      loading.end();
    }
  };

  /** 2023/05/23 - buttonRef - by 1-blue */
  const buttonRef = useRef<HTMLButtonElement>(null);

  /** 2023/05/23 - enter / shift + enter - by 1-blue */
  const onEnter: React.KeyboardEventHandler<HTMLTextAreaElement> = (e) => {
    // shift + enter
    if (e.key === "Enter" && e.shiftKey) return;

    // enter
    if (e.key === "Enter") {
      e.preventDefault();

      buttonRef.current?.click();
    }
  };

  return (
    <form className="flex flex-col pt-4 animate-fade-down" onSubmit={onSubmitComment}>
      <textarea
        ref={textareaRef}
        value={content}
        onChange={(e) => {
          setContent(e.target.value);
          handleResizeHeight();
        }}
        onKeyDown={onEnter}
        className="resize-none bg-gray-200 w-full min-h-[60px] focus:outline-main-300 rounded-md p-2 focus:bg-gray-100 shadow-sm focus:shadow-md"
      />
      <button
        type="submit"
        className="ml-auto mt-3 px-3 py-2 text-sm bg-main-400 text-white font-bold rounded-sm transition-colors hover:bg-main-500 active:bg-main-600"
        ref={buttonRef}
      >
        답글 작성
      </button>
    </form>
  );
};

export default BoardRecommentForm;
