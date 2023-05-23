import { serverInstance } from ".";

// type
import type { ApiFetchKeywordsHandler, ApiFetchKeywordsResponse } from "@/types/api";

/** 2023/05/23 - 주간 키워드 요청 - by 1-blue */
export const apiFetchDailyKeywords: ApiFetchKeywordsHandler = async (params) => {
  const { data } = await serverInstance.get<ApiFetchKeywordsResponse>(`/keyword/daily`, { params });

  return data;
};
/** 2023/05/23 - 일간 키워드 요청 - by 1-blue */
export const apiFetchWeeklyKeywords: ApiFetchKeywordsHandler = async (params) => {
  const { data } = await serverInstance.get<ApiFetchKeywordsResponse>(`/keyword/weekly`, { params });

  return data;
};
/** 2023/05/23 - 월간 키워드 요청 - by 1-blue */
export const apiFetchMonthlyKeywords: ApiFetchKeywordsHandler = async (params) => {
  const { data } = await serverInstance.get<ApiFetchKeywordsResponse>(`/keyword/monthly`, { params });

  return data;
};
