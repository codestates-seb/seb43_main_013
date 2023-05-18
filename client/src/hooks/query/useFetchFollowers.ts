import { useInfiniteQuery } from "@tanstack/react-query";

// api
import { apiFetchFollowers } from "@/apis";

// type
import type { ApiFetchFollowersRequest, ApiFetchFollowersResponse } from "@/types/api";

/** 2023/05/18 - 특정 유저 팔로워들 패치하는 훅 - by leekoby */
const useFetchFollowers = ({ memberId, page, size }: ApiFetchFollowersRequest) => {
  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery<ApiFetchFollowersResponse>(
    ["followers", memberId],
    ({ pageParam = page }) => apiFetchFollowers({ memberId, page: pageParam, size }),
    {
      getNextPageParam: (lastPage, allPage) =>
        lastPage.pageInfo.totalPages > lastPage.pageInfo.page ? lastPage.pageInfo.page + 1 : null,
    },
  );

  return { data, fetchNextPage, hasNextPage, isFetching };
};

export { useFetchFollowers };
