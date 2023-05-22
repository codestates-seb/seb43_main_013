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
  followerCount?: number;
  followingCount?: number;
  followed?: boolean;
  myPage?: boolean;
}

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
