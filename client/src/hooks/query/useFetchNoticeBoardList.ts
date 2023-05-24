import { useInfiniteQuery } from "@tanstack/react-query";

//api
import { apiFetchNoticeBoardList } from "@/apis/board/notice";

import { QUERY_KEYS } from "@/hooks/query";
import { ApiFetchNoticeBoardListRequest, ApiFetchNoticeBoardListResponse } from "@/types/api";

/** 2023/05/20 - 공지사항 게시글 리스트 정보 패치하는 훅 - by leekoby */
const useFetchNoticeBoardList = ({ sorted, page, size }: ApiFetchNoticeBoardListRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching, refetch, isPreviousData } =
    useInfiniteQuery<ApiFetchNoticeBoardListResponse>(
      [QUERY_KEYS.noticeBoardList, page],
      ({ pageParam = page }) => apiFetchNoticeBoardList({ sorted, page: pageParam, size }),
      {
        getNextPageParam: (lastPage, allPage) =>
          lastPage?.pageInfo?.size === size ? lastPage?.pageInfo?.page + 1 : null,
      },
    );

  return { data, fetchNextPage, hasNextPage, isFetching, refetch, isPreviousData };
};

export { useFetchNoticeBoardList };
