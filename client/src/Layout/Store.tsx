import { create } from "zustand";

interface Token {
  accessToken: string;
  refreshToken: string;
  setAccessToken: (accessToken: string) => void;
  setRefreshToken: (refreshToken: string) => void;
}

/** 2023/05/13 - 로그인 응답 헤더 토큰 저장소 - by Kadesti */
const TokenStore = create<Token>((set) => ({
  accessToken: "",
  refreshToken: "",
  setAccessToken: (accessToken) => set(() => ({ accessToken })),
  setRefreshToken: (refreshToken) => set(() => ({ refreshToken })),
}));

interface UserInfo {
  memberId: number;
  email: string;
  name: string;
  nickname: string;
  phone: string;
  oauth: boolean;
  introduction: string;
  link: string;
  profileImageUrl: string;
  createdAt: string;
  modifiedAt: string;

  setMemberId: (memberId: number) => void;
  saveEmail: (refreshToken: string) => void;
  setName: (name: string) => void;
  setNickname: (nickname: string) => void;
  setPhone: (phone: string) => void;
  setOauth: (oauth: boolean) => void;
  setIntroduction: (introduction: string) => void;
  setLink: (link: string) => void;
  setProfileImageUrl: (profileImageUrl: string) => void;
  setCreatedAt: (createdAt: string) => void;
  setModifiedAt: (modifiedAt: string) => void;
}

/** 2023/05/13 - 로그인 응답 데이터 저장소 - by Kadesti */
const UserInfoStore = create<UserInfo>((set) => ({
  memberId: 0,
  email: "",
  name: "",
  nickname: "",
  phone: "",
  oauth: false,
  introduction: "",
  link: "",
  profileImageUrl: "",
  createdAt: "",
  modifiedAt: "",

  setMemberId: (memberId) => set(() => ({ memberId })),
  saveEmail: (email) => set(() => ({ email })),
  setName: (name) => set(() => ({ name })),
  setNickname: (nickname) => set(() => ({ nickname })),
  setPhone: (phone) => set(() => ({ phone })),
  setOauth: (oauth) => set(() => ({ oauth })),
  setIntroduction: (introduction) => set(() => ({ introduction })),
  setLink: (link) => set(() => ({ link })),
  setProfileImageUrl: (profileImageUrl) => set(() => ({ profileImageUrl })),
  setCreatedAt: (createdAt) => set(() => ({ createdAt })),
  setModifiedAt: (modifiedAt) => set(() => ({ modifiedAt })),
}));

export { TokenStore, UserInfoStore };
