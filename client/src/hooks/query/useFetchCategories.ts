import { useQuery } from "@tanstack/react-query";

// api
import {
  apiFetchCategories,
  apiFetchFeedbackCategories,
  apiFetchJobCategories,
  apiFetchPromotionCategories,
} from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type {
  ApiFetchCategoriesResponse,
  ApiFetchFeedbackCategoriesResponse,
  ApiFetchJobCategoriesResponse,
  ApiFetchPromotionCategoriesResponse,
  CategoryType,
} from "@/types/api";

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

/** 2023/05/14 - 피드백 게시판 카테고리들을 패치하는 훅 - by leekoby */
const useFetchFeedbackCategories = ({ type }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchFeedbackCategoriesResponse>([QUERY_KEYS.feedbackCategories, type], () =>
    apiFetchFeedbackCategories({ type }),
  );

  return { feedbackCategories: data, feedbackCategoryIsLoading: isLoading };
};

export { useFetchFeedbackCategories };

/** 2023/05/17 - 홍보 게시판 카테고리들을 패치하는 훅 - by leekoby */
const useFetchPromotionCategories = ({ type }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchPromotionCategoriesResponse>(
    [QUERY_KEYS.promotionCategories, type],
    () => apiFetchPromotionCategories({ type }),
  );

  return { promotionCategories: data, promotionCategoryIsLoading: isLoading };
};

export { useFetchPromotionCategories };

/** 2023/05/18 - 구인구직 게시판 카테고리들을 패치하는 훅 - by leekoby */
const useFetchJobCategories = ({ type }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchJobCategoriesResponse>([QUERY_KEYS.jobCategories, type], () =>
    apiFetchJobCategories({ type }),
  );

  return { jobCategories: data, jobCategoryIsLoading: isLoading };
};

export { useFetchJobCategories };
