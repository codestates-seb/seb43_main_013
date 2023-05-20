import { create } from "zustand";

interface UseLogInModal {
  isShowLogInModal: boolean;

  openLogInModal: () => void;
  closeLogInModal: () => void;
  toggleLogInModal: () => void;
}

/** 2023/05/20 - 로그인 모달 상태 저장소 - by 1-blue */
const useLogInModal = create<UseLogInModal>((set) => ({
  isShowLogInModal: false,

  openLogInModal: () => set((prev) => ({ ...prev, isShowLogInModal: true })),
  closeLogInModal: () => set((prev) => ({ ...prev, isShowLogInModal: false })),
  toggleLogInModal: () => set((prev) => ({ ...prev, isShowLogInModal: !prev.isShowLogInModal })),
}));

export default useLogInModal;
