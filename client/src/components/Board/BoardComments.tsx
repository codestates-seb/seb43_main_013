import { useCallback } from "react";
import { useToast } from "@chakra-ui/react";
import { type InfiniteData, useQueryClient } from "@tanstack/react-query";

import { QUERY_KEYS } from "@/hooks/query";

// api
import { apiDeleteComment } from "@/apis";

// hook
import { useFetchComments } from "@/hooks/query/useFetchComments";
import useLoading from "@/hooks/useLoading";

// component
import BoardComment from "./BoardComment";
import Skeleton from "@/components/Skeleton";

// type
import type { ApiFetchCommentsResponse, BoardType } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
}

/** 2023/05/11 - 게시판의 댓글들 컴포넌트 - by 1-blue */
const BoardComments: React.FC<Props> = ({ type, boardId }) => {
  const toast = useToast();
  const actions = useLoading();

  const { data, fetchNextPage, hasNextPage, isFetching } = useFetchComments({
    type,
    boardId,
    page: 1,
    size: 10,
  });

  /** 2023/05/11 - 댓글 추가를 위해 사용 - by 1-blue */
  const queryClient = useQueryClient();

  /** 2023/05/11 - 댓글 삭제 ( 버블링 ) - by 1-blue */
  const onDeleteComment: React.MouseEventHandler<HTMLUListElement> = useCallback(
    async (e) => {
      if (!(e.target instanceof HTMLButtonElement)) return;
      if (!e.target.dataset.commentId) return;

      const commentId = +e.target.dataset.commentId;

      try {
        actions.startLoading();

        await apiDeleteComment(type, { boardId, commentId });

        actions.endLoading();

        queryClient.setQueryData<InfiniteData<ApiFetchCommentsResponse> | undefined>(
          [QUERY_KEYS.comment, type],
          (prev) =>
            prev && {
              ...prev,
              pages: prev.pages.map((page) => ({
                ...page,
                data: page.data.filter((comment) => comment.commentId !== commentId),
              })),
            },
        );

        return toast({
          description: "댓글을 삭제했습니다.",
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
    },
    [queryClient],
  );

  return (
    <>
      <ul className="space-y-4" onClick={onDeleteComment}>
        {data?.pages.map((page) =>
          page.data.map((comment) => (
            <BoardComment key={comment.commentId} type={type} boardId={boardId} comment={comment} />
          )),
        )}
      </ul>

      {isFetching && <Skeleton.Comment count={8} />}

      {hasNextPage && (
        <section className="flex">
          <button
            type="button"
            onClick={() => fetchNextPage()}
            className="ml-auto py-2 my-3 px-3 bg-main-400 text-white text-xs font-bold rounded-sm transition-colors hover:bg-main-500 active:bg-main-600"
          >
            댓글 더 불러오기
          </button>
        </section>
      )}
    </>
  );
};

export default BoardComments;
