import { create } from "zustand";

type PageState = {
  currentPage: number;
  setCurrentPage: (page: number) => void;
  resetPageState: () => void;
};

/** 2023/05/14 - 새로고침시 로컬 스토리지에서 페이지 상태 읽어오기 - by leekoby */
const getPageStateFromStorage = (): number => {
  if (typeof window !== "undefined") {
    const pageState = localStorage.getItem("currentPage");
    return pageState ? parseInt(pageState, 10) : 1;
  } else return 1;
};
/** 2023/05/14 - 페이지 상태 로컬 스토리지에 저장하기 - by leekoby */
const setPageStateToStorage = (page: number) => {
  localStorage.setItem("currentPage", page.toString());
};

/** 2023/05/14 - page 전역 상태 관리 - by leekoby */
export const usePageStore = create<PageState>((set) => ({
  currentPage: getPageStateFromStorage(),
  setCurrentPage: (page: number) => {
    setPageStateToStorage(page);
    set(() => ({ currentPage: page }));
  },
  resetPageState: () => {
    setPageStateToStorage(1);
    set(() => ({ currentPage: 1 }));
  },
}));
