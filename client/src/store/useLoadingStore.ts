import { create } from "zustand";

interface LoadingType {
  isLoading: boolean;
  start: () => void;
  end: () => void;
}

/** 2023/05/12 - 로딩 상태 - by 1-blue */
export const useLoadingStore = create<LoadingType>((set) => ({
  isLoading: false,
  start: () => set({ isLoading: true }),
  end: () => set({ isLoading: false }),
}));
