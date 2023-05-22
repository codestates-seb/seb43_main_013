import { useInfiniteQuery } from "@tanstack/react-query";
// api
import { apiFetchFreeBoardList } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchFreeBoardListRequest, ApiFetchFreeBoardListResponse } from "@/types/api";

/** 2023/05/11 - 자유 게시판 게시글 리스트 정보 패치하는 훅 - by leekoby */
const useFetchFreeBoardList = ({ selected, sorted, page, size }: ApiFetchFreeBoardListRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching, refetch, isPreviousData } =
    useInfiniteQuery<ApiFetchFreeBoardListResponse>(
      [QUERY_KEYS.freeBoardList, page],
      ({ pageParam = page }) => apiFetchFreeBoardList({ selected, sorted, page: pageParam, size }),
      {
        getNextPageParam: (lastPage, allPage) =>
          lastPage.pageInfo.totalPages > lastPage.pageInfo.page ? lastPage.pageInfo.page + 1 : null,
      },
    );

  return { data, fetchNextPage, hasNextPage, isFetching, refetch, isPreviousData };
};

export { useFetchFreeBoardList };
