import { create } from "zustand";

interface PromotionCategoryState {
  selectedPromotionCategory: {
    promotionCategoryName: string;
    promotionCategoryId: number;
  };
  setSelectedPromotionCategory: (promotionCategoryName: string, promotionCategoryId: number) => void;
  resetPromotionCategoryState: () => void;
}

/** 2023/05/17 - 새로고침시 로컬 스토리지에서 홍보 카테고리 상태 읽어오기 - by leekoby */
const getPromotionCategoryStateFromStorage = () => {
  if (typeof window !== "undefined") {
    const promotionCategoryState = localStorage.getItem("currentPromotionCategory");
    return promotionCategoryState
      ? JSON.parse(promotionCategoryState)
      : { promotionCategoryName: "전체", promotionCategoryId: 1 };
  }
};

/** 2023/05/17 - 홍보 카테고리 상태 로컬 스토리지에 저장하기 - by leekoby */
const setPromotionCategoryStateToStorage = (promotionCategory: {
  promotionCategoryName: string;
  promotionCategoryId: number;
}) => {
  if (typeof window !== "undefined") {
    localStorage.setItem("currentPromotionCategory", JSON.stringify(promotionCategory));
  }
};

/** 2023/05/17- 홍보 카테고리 전역 상태 관리 - by leekoby */
export const usePromotionCategoriesStore = create<PromotionCategoryState>((set) => ({
  selectedPromotionCategory: getPromotionCategoryStateFromStorage(),
  setSelectedPromotionCategory: (promotionCategoryName: string, promotionCategoryId: number) => {
    const promotionCategoryObj = { promotionCategoryName, promotionCategoryId };
    setPromotionCategoryStateToStorage(promotionCategoryObj);
    set({ selectedPromotionCategory: promotionCategoryObj });
  },
  resetPromotionCategoryState: () => {
    localStorage.removeItem("currentPromotionCategory");
    set({ selectedPromotionCategory: { promotionCategoryName: "전체", promotionCategoryId: 1 } });
  },
}));
