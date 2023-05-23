import { serverInstance } from "./";

//type

import type { ApiFetchYoutubeListHandler, ApiFetchYoutubeListRequest, ApiFetchYoutubeListResponse } from "@/types/api";

/** 2023/05/22 - 유튜브 인기동영상 리스트 요청 - by leekoby */
export const apiFetchYoutubeList: ApiFetchYoutubeListHandler = async ({ youtubeCategoryId = 3 }) => {
  const { data } = await serverInstance.get<ApiFetchYoutubeListResponse>(
    `/youtubevideos/categories/${youtubeCategoryId}`,
  );

  return data;
};
