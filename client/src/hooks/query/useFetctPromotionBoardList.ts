import { useInfiniteQuery } from "@tanstack/react-query";

//api
import { apiFetchPromotionBoardList } from "@/apis";

//key
import { QUERY_KEYS } from "@/hooks/query";

//type
import type { ApiFetchPromotionBoardListRequest, ApiFetchPromotionBoardListResponse } from "@/types/api";

/** 2023/05/17 - 홍보 게시판 게시글 리스트 정보 패치하는 훅 - by leekoby */
const useFetchPromotionBoardList = ({
  selected,
  selectedPromotion,
  sorted,
  page,
  size,
}: ApiFetchPromotionBoardListRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching, refetch, error, isError } =
    useInfiniteQuery<ApiFetchPromotionBoardListResponse>(
      [QUERY_KEYS.promotionBoardList],
      ({ pageParam = page }) =>
        apiFetchPromotionBoardList({ selected, selectedPromotion, sorted, page: pageParam, size }),
      {
        getNextPageParam: (lastPage, allPage) =>
          lastPage.pageInfo.totalPages > lastPage.pageInfo.page ? lastPage.pageInfo.page + 1 : null,
      },
    );

  return { data, fetchNextPage, hasNextPage, isFetching, refetch, error, isError };
};

export default useFetchPromotionBoardList;
