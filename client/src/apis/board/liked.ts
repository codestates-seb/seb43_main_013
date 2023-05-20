import { serverInstance } from "../";

// type
import type { ApiFetchLikedBoardListHandler, ApiFetchLikedBoardListResponse } from "@/types/api";

/** 2023/05/17 - 내가 좋아요 누른 게시들 리스트 패치 요청 - by 1-blue */
export const apiFetchLikedBoardList: ApiFetchLikedBoardListHandler = async ({ memberId, ...params }) => {
  const { data } = await serverInstance.get<ApiFetchLikedBoardListResponse>(`/member/${memberId}/liked`, {
    params,
  });

  return data;
};
