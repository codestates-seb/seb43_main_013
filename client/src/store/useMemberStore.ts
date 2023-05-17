import { create } from "zustand";

interface Member {
  memberId: number;
  email: string;
  name: string;
  nickname: string;
  phone?: string;
  oauth: boolean;
  introduction?: string;
  link?: string;
  profileImageUrl: string;
  createdAt: string;
  modifiedAt: string;
  rename: string;
}

interface MemberInfo {
  member?: Member | null;

  setMember: (member: Member) => void;
}

/** 2023/05/13 - 로그인 응답 데이터 저장소 - by Kadesti */
const useMemberStore = create<MemberInfo>((set) => ({
  member: null,

  setMember: (member) => set((state) => ({ ...state, member: { ...state.member, ...member } })),
}));

export { useMemberStore };
