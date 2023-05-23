import { serverInstance } from ".";

// type
import type { ApiFetchSearchHandler, ApiFetchSearchResponse } from "@/types/api";

/** 2023/05/22 - 검색 요청 - by 1-blue */
export const apiFetchSearch: ApiFetchSearchHandler = async (params) => {
  const { data } = await serverInstance.get<ApiFetchSearchResponse>(`/search`, { params });

  return data;
};
