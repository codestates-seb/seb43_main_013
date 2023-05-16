import { create } from "zustand";

interface FeedbackCategoryState {
  selectedFeedbackCategory: {
    feedbackCategoryName: string;
    feedbackCategoryId: number;
  };
  setSelectedFeedbackCategory: (feedbackCategoryName: string, feedbackCategoryId: number) => void;
  resetFeedbackCategoryState: () => void;
}

/** 2023/05/15 - 새로고침시 로컬 스토리지에서 피드백 카테고리 상태 읽어오기 - by leekoby */
const getFeedbackCategoryStateFromStorage = () => {
  if (typeof window !== "undefined") {
    const feedbackCategoryState = localStorage.getItem("currentFeedbackCategory");
    return feedbackCategoryState
      ? JSON.parse(feedbackCategoryState)
      : { feedbackCategoryName: "전체", feedbackCategoryId: 1 };
  }
};

/** 2023/05/15 - 피드백 카테고리 상태 로컬 스토리지에 저장하기 - by leekoby */
const setFeedbackCategoryStateToStorage = (feedbackCategory: {
  feedbackCategoryName: string;
  feedbackCategoryId: number;
}) => {
  if (typeof window !== "undefined") {
    localStorage.setItem("currentFeedbackCategory", JSON.stringify(feedbackCategory));
  }
};

/** 2023/05/15- 피드백 카테고리 전역 상태 관리 - by leekoby */
export const useFeedbackCategoriesStore = create<FeedbackCategoryState>((set) => ({
  selectedFeedbackCategory: getFeedbackCategoryStateFromStorage(),
  setSelectedFeedbackCategory: (feedbackCategoryName: string, feedbackCategoryId: number) => {
    const feedbackCategoryObj = { feedbackCategoryName, feedbackCategoryId };
    setFeedbackCategoryStateToStorage(feedbackCategoryObj);
    set({ selectedFeedbackCategory: feedbackCategoryObj });
  },
  resetFeedbackCategoryState: () => {
    localStorage.removeItem("currentFeedbackCategory");
    set({ selectedFeedbackCategory: { feedbackCategoryName: "전체", feedbackCategoryId: 1 } });
  },
}));
