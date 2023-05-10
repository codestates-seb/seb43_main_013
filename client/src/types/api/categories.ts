import type { CategoryType } from ".";

// ============================== 카테고리들 ==============================
/** 2023/05/09 - 카테고리들 요청 송신 타입 - by 1-blue */
export interface ApiFetchCategoriesRequest {
  type: CategoryType;
}
/** 2023/05/09 - 카테고리들 요청 수신 타입 - by 1-blue */
export interface ApiFetchCategoriesResponse {
  categories: string[];
}
/** 2023/05/09 - 카테고리들 요청 핸들러 - by 1-blue */
export interface ApiFetchCategoriesHandler {
  (body: ApiFetchCategoriesRequest): Promise<ApiFetchCategoriesResponse>;
}
