import { serverInstance } from ".";

// type
import type {
  ApiFetchCategoriesHandler,
  ApiFetchCategoriesResponse,
  ApiFetchFeedbackCategoriesHandler,
  ApiFetchFeedbackCategoriesResponse,
  ApiFetchJobCategoriesHandler,
  ApiFetchJobCategoriesResponse,
  ApiFetchPromotionCategoriesHandler,
  ApiFetchPromotionCategoriesResponse,
} from "@/types/api";

/** 2023/05/09 - 카테고리들 패치 요청 - by 1-blue */
export const apiFetchCategories: ApiFetchCategoriesHandler = async (body) => {
  const { data } = await serverInstance.get<ApiFetchCategoriesResponse>("/categories", {
    params: body,
  });

  return data;
};
/** 2023/05/13 - 피드백 게시판 카테고리 패치 요청 - by leekoby */
export const apiFetchFeedbackCategories: ApiFetchFeedbackCategoriesHandler = async (body) => {
  const { data } = await serverInstance.get<ApiFetchFeedbackCategoriesResponse>("/feedbackcategories", {
    params: body,
  });

  return data;
};

/** 2023/05/17 - 홍보 게시판 카테고리 패치 요청 - by leekoby */
export const apiFetchPromotionCategories: ApiFetchPromotionCategoriesHandler = async (body) => {
  const { data } = await serverInstance.get<ApiFetchPromotionCategoriesResponse>("/promotioncategories", {
    params: body,
  });

  return data;
};

/** 2023/05/13 - 구인구직 게시판 카테고리 패치요청 - by leekoby */
/** 2023/05/18 - 구인구직 게시판 카테고리 패치요청 - by leekoby */
export const apiFetchJobCategories: ApiFetchJobCategoriesHandler = async (body) => {
  const { data } = await serverInstance.get<ApiFetchJobCategoriesResponse>("/jobcategories", {
    params: body,
  });

  return data;
};
