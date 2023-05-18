import { serverInstance } from "../";

// type
import type { ApiFetchBookmarkedBoardListHandler, ApiFetchBookmarkedBoardListResponse } from "@/types/api";

/** 2023/05/17 - 내가 북마크한 게시들 리스트 패치 요청 - by 1-blue */
export const apiFetchBookmarkedBoardList: ApiFetchBookmarkedBoardListHandler = async ({ memberId, ...params }) => {
  const { data } = await serverInstance.get<ApiFetchBookmarkedBoardListResponse>(`/member/${memberId}/bookmarked`, {
    params,
  });

  return data;
};
