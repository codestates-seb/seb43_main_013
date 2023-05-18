import { serverInstance } from "../";

// type
import type { ApiFetchWrittenBoardListHandler, ApiFetchWrittenBoardListResponse } from "@/types/api";

/** 2023/05/17 - 내가 작성한 게시들 리스트 패치 요청 - by 1-blue */
export const apiFetchWrittenBoardList: ApiFetchWrittenBoardListHandler = async ({ memberId, ...params }) => {
  const { data } = await serverInstance.get<ApiFetchWrittenBoardListResponse>(`/member/${memberId}/written`, {
    params,
  });

  return data;
};
