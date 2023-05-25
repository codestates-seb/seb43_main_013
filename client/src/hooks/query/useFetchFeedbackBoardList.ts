import { useInfiniteQuery } from "@tanstack/react-query";

// api
import { apiFetchFeedbackBoardList } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchFeedbackBoardListRequest, ApiFetchFeedbackBoardListResponse } from "@/types/api";

/** 2023/05/12- 피드백 게시판 게시글 리스트 정보 패치하는 훅 - by leekoby */
const useFetchFeedbackBoardList = ({
  selected,
  selectedFeedback,
  sorted,
  page,
  size,
}: ApiFetchFeedbackBoardListRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching, refetch, isError, isLoading, error } =
    useInfiniteQuery<ApiFetchFeedbackBoardListResponse>(
      [QUERY_KEYS.feedbackBoardList],
      ({ pageParam = page }) =>
        apiFetchFeedbackBoardList({ selected, selectedFeedback, sorted, page: pageParam, size }),
      {
        getNextPageParam: (lastPage, allPage) =>
          lastPage?.pageInfo?.size === size ? lastPage?.pageInfo?.page + 1 : null,
      },
    );

  return { data, fetchNextPage, hasNextPage, isFetching, refetch, isError, isLoading, error };
};

export { useFetchFeedbackBoardList };
