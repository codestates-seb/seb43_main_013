import { create } from "zustand";

interface UseSignUpModal {
  isShowSignUpModal: boolean;

  openSignUpModal: () => void;
  closeSignUpModal: () => void;
  toggleSignUpModal: () => void;
}

/** 2023/05/20 - 회원가입 모달 상태 저장소 - by 1-blue */
const useSignUpStore = create<UseSignUpModal>((set) => ({
  isShowSignUpModal: false,

  openSignUpModal: () => set((prev) => ({ ...prev, isShowSignUpModal: true })),
  closeSignUpModal: () => set((prev) => ({ ...prev, isShowSignUpModal: false })),
  toggleSignUpModal: () => set((prev) => ({ ...prev, isShowSignUpModal: !prev.isShowSignUpModal })),
}));

export default useSignUpStore;
