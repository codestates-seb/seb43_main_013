import { serverInstance } from ".";

// type
import type { ApiFetchCategoriesHandler, ApiFetchCategoriesResponse } from "@/types/api";

/** 2023/05/09 - 카테고리들 패치 요청 - by 1-blue */
export const apiFetchCategories: ApiFetchCategoriesHandler = async (body) => {
  const { data } = await serverInstance.get<ApiFetchCategoriesResponse>("/categories", {
    params: body,
  });

  return data;
};
