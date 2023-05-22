import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchLikedBoardList } from "@/apis";

// type
import type { ApiFetchLikedBoardListRequest, ApiFetchLikedBoardListResponse } from "@/types/api";

/** 2023/05/17 - 내가 좋아요 누른 게시글 리스트 패치하는 훅 - by leekoby */
const useFetchLikedBoardList = ({ memberId, page = 1, size }: ApiFetchLikedBoardListRequest) => {
  const { data, isFetching } = useQuery<ApiFetchLikedBoardListResponse>(["profile", "board", "liked", page], () =>
    apiFetchLikedBoardList({ memberId, page, size }),
  );

  return { data, isFetching };
};

export { useFetchLikedBoardList };
