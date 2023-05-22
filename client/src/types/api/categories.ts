import type {
  CategoryType,
  ResponseCategoriesType,
  ResponseFeedbackCategoriesType,
  ResponseJobCategoriesType,
  ResponsePromotionCategoriesType,
} from ".";

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

// ============================== 홍보 카테고리들 ==============================
/** 2023/05/17 - 홍보 카테고리들 요청 송신 타입 - by leekoby */
export interface ApiFetchPromotionCategoriesRequest {
  type: CategoryType;
}
/** 2023/05/17 - 홍보 카테고리들 요청 수신 타입 - by leekoby */
export type ApiFetchPromotionCategoriesResponse = ResponsePromotionCategoriesType[];

/** 2023/05/17 - 홍보 카테고리들 요청 핸들러 - by leekoby */
export interface ApiFetchPromotionCategoriesHandler {
  (body: ApiFetchPromotionCategoriesRequest): Promise<ApiFetchPromotionCategoriesResponse>;
}

// ============================== 구인구직 카테고리들 ==============================
/** 2023/05/17 - 구인구직 카테고리들 요청 송신 타입 - by leekoby */
export interface ApiFetchJobCategoriesRequest {
  type: CategoryType;
}
/** 2023/05/17 - 구인구직 카테고리들 요청 수신 타입 - by leekoby */
export type ApiFetchJobCategoriesResponse = ResponseJobCategoriesType[];

/** 2023/05/17 - 구인구직 카테고리들 요청 핸들러 - by leekoby */
export interface ApiFetchJobCategoriesHandler {
  (body: ApiFetchJobCategoriesRequest): Promise<ApiFetchJobCategoriesResponse>;
}
