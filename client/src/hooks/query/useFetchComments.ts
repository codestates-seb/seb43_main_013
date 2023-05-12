import { useInfiniteQuery } from "@tanstack/react-query";

// api
import { apiFetchComments } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchCommentsResponse, BoardType } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
  page: number;
  size: number;
}

/** 2023/05/11 - 게시글의 댓글들 조회 상세 정보 패치하는 훅 - by 1-blue */
const useFetchComments = ({ type, boardId, page, size }: Props) => {
  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery<ApiFetchCommentsResponse>(
    [QUERY_KEYS.comment, type],
    ({ pageParam = page }) => apiFetchComments(type, { boardId, page: pageParam, size }),
    {
      getNextPageParam: (lastPage, allPage) =>
        lastPage.pageInfo.totalPages > lastPage.pageInfo.page ? lastPage.pageInfo.page + 1 : null,
    },
  );

  return { data, fetchNextPage, hasNextPage, isFetching };
};

export { useFetchComments };
