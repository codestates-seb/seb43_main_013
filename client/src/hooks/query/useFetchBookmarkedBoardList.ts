import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchBookmarkedBoardList } from "@/apis";

// type
import type { ApiFetchBookmarkedBoardListRequest, ApiFetchBookmarkedBoardListResponse } from "@/types/api";

/** 2023/05/17 - 내가 북마크한 게시글 리스트 패치하는 훅 - by leekoby */
const useFetchBookmarkedBoardList = ({ memberId, page = 1, size }: ApiFetchBookmarkedBoardListRequest) => {
  const { data, isFetching } = useQuery<ApiFetchBookmarkedBoardListResponse>(
    ["profile", "board", "bookmarked", page],
    () => apiFetchBookmarkedBoardList({ memberId, page, size }),
  );

  return { data, isFetching };
};

export { useFetchBookmarkedBoardList };
