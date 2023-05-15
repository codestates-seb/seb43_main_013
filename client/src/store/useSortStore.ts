import { create } from "zustand";

interface SortState {
  selectedOption: {
    optionId: number;
    optionName: string;
  };
  setSelectedOption: (optionName: string, optionId: number) => void;
  resetSelectedOption: () => void;
}

/** 2023/05/15 - 새로고침시 로컬 스토리지에서 사이드 정렬 상태 읽어오기- by leekoby */
const getSortOptionStateFromStorage = () => {
  if (typeof window !== "undefined") {
    const sortOptionState = localStorage.getItem("currentSortOption");
    return sortOptionState ? JSON.parse(sortOptionState) : { optionId: 1, optionName: "최신순" };
  }
};

/** 2023/05/15 - 정렬 상태 로컬 스토리지에 저장하기 - by leekoby */
const setSortOptionStateToStorage = (selectedOption: { optionId: number; optionName: string }) => {
  if (typeof window !== "undefined") {
    localStorage.setItem("currentSortOption", JSON.stringify(selectedOption));
  }
};

/** 2023/05/15 - 정렬 옵션 전역 상태 관리 - by leekoby */
export const useSortStore = create<SortState>((set) => ({
  selectedOption: getSortOptionStateFromStorage(),
  setSelectedOption: (optionName: string, optionId: number) => {
    const sortOptionObj = { optionName, optionId };
    setSortOptionStateToStorage(sortOptionObj);
    set({ selectedOption: sortOptionObj });
  },
  resetSelectedOption: () => {
    if (typeof window !== "undefined") {
      localStorage.removeItem("currentSortOption");
    }
    set({ selectedOption: { optionId: 1, optionName: "최신순" } });
  },
}));
