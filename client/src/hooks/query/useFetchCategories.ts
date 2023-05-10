import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchCategories } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchCategoriesResponse, CategoryType } from "@/types/api";
interface Props {
  type: CategoryType;
}

/** 2023/05/09 - 카테고리들을 패치하는 훅 - by 1-blue */
const useFetchCategories = ({ type }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchCategoriesResponse>([QUERY_KEYS.categories, type], () =>
    apiFetchCategories({ type }),
  );

  return { categories: data?.categories, isLoading };
};

export { useFetchCategories };
