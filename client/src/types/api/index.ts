/** 2023/05/09 - 요청 가능한 카테고리 타입 - by 1-blue */
export type CategoryType = "normal" | "feedback" | "job";

/** 2023/05/10 - 페이지 정보 타입 - by 1-blue */
export interface PageInfo {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export * from "./categories";
export * from "./board";
