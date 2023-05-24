import { useInfiniteQuery } from "@tanstack/react-query";

// api
import { apiFetchSearch } from "@/apis";

// type
import type { ApiFetchSearchRequest, ApiFetchSearchResponse } from "@/types/api";
interface Props extends ApiFetchSearchRequest {
  initialData?: ApiFetchSearchResponse;
}

/** 2023/05/22 - 검색 훅 - by 1-blue */
const useFetchSearch = ({ keyword, page, size, initialData }: Props) => {
  const { data, fetchNextPage, hasNextPage, isFetching, refetch } = useInfiniteQuery<ApiFetchSearchResponse>(
    ["search", keyword],
    ({ pageParam = page }) => apiFetchSearch({ keyword, page: pageParam, size }),
    {
      getNextPageParam: (lastPage, allPage) => (lastPage.pageInfo.size === size ? lastPage.pageInfo.page + 1 : null),
      // ssr
      ...(initialData && {
        initialData: { pageParams: [], pages: [initialData] },
      }),
    },
  );

  return { data, fetchNextPage, hasNextPage, isFetching, refetch };
};

export { useFetchSearch };
