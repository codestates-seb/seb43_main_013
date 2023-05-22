import { useInfiniteQuery } from "@tanstack/react-query";

// api
import { apiFetchFollowings } from "@/apis";

// type
import type { ApiFetchFollowingsRequest, ApiFetchFollowingsResponse } from "@/types/api";

/** 2023/05/18 - 특정 유저 팔로잉들 패치하는 훅 - by leekoby */
const useFetchFollowings = ({ memberId, page, size }: ApiFetchFollowingsRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery<ApiFetchFollowingsResponse>(
    ["followings", memberId],
    ({ pageParam = page }) => apiFetchFollowings({ memberId, page: pageParam, size }),
    {
      getNextPageParam: (lastPage, allPage) =>
        lastPage.pageInfo.totalPages > lastPage.pageInfo.page ? lastPage.pageInfo.page + 1 : null,
    },
  );

  return { data, fetchNextPage, hasNextPage, isFetching };
};

export { useFetchFollowings };
