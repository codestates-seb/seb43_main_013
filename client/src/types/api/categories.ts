import type { CategoryType, ResponseCategoriesType, ResponseFeedbackCategoriesType } from ".";

// ============================== 카테고리들 ==============================
/** 2023/05/09 - 카테고리들 요청 송신 타입 - by 1-blue */
export interface ApiFetchCategoriesRequest {
  type: CategoryType;
}
/** 2023/05/09 - 카테고리들 요청 수신 타입 - by 1-blue */
/** 2023/05/14 - 카테고리들 요청 수신 타입 수정 - by leekoby */
export type ApiFetchCategoriesResponse = ResponseCategoriesType[];

/** 2023/05/09 - 카테고리들 요청 핸들러 - by 1-blue */
export interface ApiFetchCategoriesHandler {
  (body: ApiFetchCategoriesRequest): Promise<ApiFetchCategoriesResponse>;
}

// ============================== 피드백 카테고리들 ==============================
/** 2023/05/14 - 피드백 카테고리들 요청 송신 타입 - by leekoby */
export interface ApiFetchFeedbackCategoriesRequest {
  type: CategoryType;
}
/** 2023/05/14 - 피드백 카테고리들 요청 수신 타입 - by leekoby */
export type ApiFetchFeedbackCategoriesResponse = ResponseFeedbackCategoriesType[];

/** 2023/05/14 - 피드백 카테고리들 요청 핸들러 - by leekoby */
export interface ApiFetchFeedbackCategoriesHandler {
  (body: ApiFetchFeedbackCategoriesRequest): Promise<ApiFetchFeedbackCategoriesResponse>;
}
