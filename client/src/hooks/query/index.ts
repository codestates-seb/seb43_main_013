/** 2023/05/09 - "react-query"에서 사용하는 key - by 1-blue */
export const QUERY_KEYS = {
  categories: "categories",
  freeBoard: "freeBoard",
  feedbackBoard: "feedbackBoard",
  promotionBoard: "promotionBoard",
  jobBoard: "jobBoard",
  comment: "comment",

  /** 2023/05/12 - "react-query"에서 사용하는 key - by leekoby */
  freeBoardList: "freeBoardList",
  feedbackBoardList: "feedbackBoardList",
  promotionBoardList: "promotionBoardList",
  jobBoardList: "jobBoardList",
};

export * from "./useFetchCategories";
export * from "./useFetchFreeBoard";
export * from "./useFetchFeedbackBoard";
export * from "./useFetchPromotionBoard";
export * from "./useFetchJobBoard";
