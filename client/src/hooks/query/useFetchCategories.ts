import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchCategories, apiFetchFeedbackCategories } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchCategoriesResponse, ApiFetchFeedbackCategoriesResponse, CategoryType } from "@/types/api";

interface Props {
  type: CategoryType;
}

/** 2023/05/09 - 카테고리들을 패치하는 훅 - by 1-blue */
const useFetchCategories = ({ type }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchCategoriesResponse>([QUERY_KEYS.categories, type], () =>
    apiFetchCategories({ type }),
  );

  return { categories: data, isLoading };
};

export { useFetchCategories };

/** 2023/05/14 - 피드백 카테고리들을 패치하는 훅 - by leekoby */
const useFetchFeedbackCategories = ({ type }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchFeedbackCategoriesResponse>([QUERY_KEYS.feedbackCategories, type], () =>
    apiFetchFeedbackCategories({ type }),
  );

  return { feedbackCategories: data, feedbackIsLoading: isLoading };
};

export { useFetchFeedbackCategories };
