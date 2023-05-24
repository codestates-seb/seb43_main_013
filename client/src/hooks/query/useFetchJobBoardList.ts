import { useInfiniteQuery } from "@tanstack/react-query";

// api
import { apiFetchJobBoardList } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

//type
import type { ApiFetchJobBoardListRequest, ApiFetchJobBoardListResponse } from "@/types/api";

/** 2023/05/18- 구인구직 게시판 게시글 리스트 정보 패치하는 훅 - by leekoby */
const useFetchJobBoardList = ({ selected, selectedJob, sorted, page, size }: ApiFetchJobBoardListRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching, refetch } = useInfiniteQuery<ApiFetchJobBoardListResponse>(
    [QUERY_KEYS.jobBoardList],
    ({ pageParam = page }) => apiFetchJobBoardList({ selected, selectedJob, sorted, page: pageParam, size }),
    {
      getNextPageParam: (lastPage, allPage) => (lastPage.pageInfo.size === size ? lastPage.pageInfo.page + 1 : null),
    },
  );

  return { data, fetchNextPage, hasNextPage, isFetching, refetch };
};

export { useFetchJobBoardList };
