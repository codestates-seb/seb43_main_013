/** 2023/05/09 - 요청 가능한 카테고리 타입 - by 1-blue */
export type CategoryType = "normal" | "feedback" | "job";

/** 2023/05/10 - 페이지 정보 타입 - by 1-blue */
export interface PageInfo {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

/** 2023/05/11 - 게시판 타입 - by 1-blue */
export type BoardType = "free" | "feedback" | "promotion" | "job";

export * from "./categories";
export * from "./board";
export * from "./comment";
export * from "./recomment";
