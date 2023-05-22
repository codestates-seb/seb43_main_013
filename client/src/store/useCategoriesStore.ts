import { create } from "zustand";

interface CategoryState {
  selectedCategory: {
    categoryName: string;
    categoryId: number;
  };
  setSelectedCategory: (categoryName: string, categoryId: number) => void;
  resetCategoryState: () => void;
}

/** 2023/05/14 - 새로고침시 로컬 스토리지에서 사이드 카테고리 상태 읽어오기 - by leekoby */
const getCategoryStateFromStorage = () => {
  if (typeof window !== "undefined") {
    const categoryState = localStorage.getItem("currentCategory");
    return categoryState ? JSON.parse(categoryState) : { categoryName: "전체", categoryId: 1 };
  }
};

/** 2023/05/14 - 사이드 카테고리 상태 로컬 스토리지에 저장하기 - by leekoby */
const setCategoryStateToStorage = (category: { categoryName: string; categoryId: number }) => {
  if (typeof window !== "undefined") {
    localStorage.setItem("currentCategory", JSON.stringify(category));
  }
};

/** 2023/05/14 - 사이드 카테고리 전역 상태 관리 - by leekoby */
export const useCategoriesStore = create<CategoryState>((set) => ({
  selectedCategory: getCategoryStateFromStorage(),
  setSelectedCategory: (categoryName: string, categoryId: number) => {
    const categoryObj = { categoryName, categoryId };
    setCategoryStateToStorage(categoryObj);
    set({ selectedCategory: categoryObj });
  },
  resetCategoryState: () => {
    localStorage.removeItem("currentCategory");
    set({ selectedCategory: { categoryName: "전체", categoryId: 1 } });
  },
}));
