import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchDailyKeywords, apiFetchWeeklyKeywords, apiFetchMonthlyKeywords } from "@/apis";

// type
import type { ApiFetchKeywordsResponse } from "@/types/api";
import { ApiFetchKeywordsHandler } from "@/types/api";

interface Props {
  page: number;
  size: number;
}

/** 2023/05/23 - 일간 핫한 키워드들 패치하는 훅 - by 1-blue */
const useFetchDailyKeywords = (body: Props) => {
  const { data, isLoading } = useQuery<ApiFetchKeywordsResponse>(["keyword", "daily"], () =>
    apiFetchDailyKeywords(body),
  );

  return { dailyKeywords: data, isLoading };
};

/** 2023/05/23 - 주간 핫한 키워드들 패치하는 훅 - by 1-blue */
const useFetchWeeklyKeywords = (body: Props) => {
  const { data, isLoading } = useQuery<ApiFetchKeywordsResponse>(["keyword", "weekly"], () =>
    apiFetchWeeklyKeywords(body),
  );

  return { weeklyKeywords: data, isLoading };
};

/** 2023/05/23 - 월간 핫한 키워드들 패치하는 훅 - by 1-blue */
const useFetchMonthlyKeywords = (body: Props) => {
  const { data, isLoading } = useQuery<ApiFetchKeywordsResponse>(["keyword", "monthly"], () =>
    apiFetchMonthlyKeywords(body),
  );

  return { MonthlyKeywords: data, isLoading };
};

export { useFetchDailyKeywords, useFetchWeeklyKeywords, useFetchMonthlyKeywords };
