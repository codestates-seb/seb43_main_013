import { create } from "zustand";

import type { Member } from "@/types/api";

interface MemberInfo {
  member?: Member | null;

  setMember: (member: Member | null) => void;
}

/** 2023/05/13 - 로그인 응답 데이터 저장소 - by Kadesti */
const useMemberStore = create<MemberInfo>((set) => ({
  member: null,

  setMember: (member) => set((state) => ({ ...state, member: member ? { ...state.member, ...member } : null })),
}));

export { useMemberStore };
