import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchDailyKeywords, apiFetchWeeklyKeywords, apiFetchMonthlyKeywords } from "@/apis";

// type
import type { ApiFetchKeywordsResponse } from "@/types/api";

/** 2023/05/23 - 일간 핫한 키워드들 패치하는 훅 - by 1-blue */
const useFetchDailyKeywords = () => {
  const { data, isLoading } = useQuery<ApiFetchKeywordsResponse>(["keyword", "daily"], () => apiFetchDailyKeywords({}));

  return { dailyKeywords: data, isLoading };
};

/** 2023/05/23 - 주간 핫한 키워드들 패치하는 훅 - by 1-blue */
const useFetchWeeklyKeywords = () => {
  const { data, isLoading } = useQuery<ApiFetchKeywordsResponse>(["keyword", "daily"], () =>
    apiFetchWeeklyKeywords({}),
  );

  return { weeklyKeywords: data, isLoading };
};

/** 2023/05/23 - 월간 핫한 키워드들 패치하는 훅 - by 1-blue */
const useFetchMonthlyKeywords = () => {
  const { data, isLoading } = useQuery<ApiFetchKeywordsResponse>(["keyword", "daily"], () =>
    apiFetchMonthlyKeywords({}),
  );

  return { MonthlyKeywords: data, isLoading };
};

export { useFetchDailyKeywords, useFetchWeeklyKeywords, useFetchMonthlyKeywords };
