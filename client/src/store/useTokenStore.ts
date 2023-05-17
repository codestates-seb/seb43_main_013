import { create } from "zustand";

interface Token {
  accessToken: string;
  refreshToken: string;
  setAccessToken: (accessToken: string) => void;
  setRefreshToken: (refreshToken: string) => void;
}

/** 2023/05/13 - 로그인 응답 헤더 토큰 저장소 - by Kadesti */
const useTokenStore = create<Token>((set) => ({
  accessToken: "",
  refreshToken: "",
  setAccessToken: (accessToken) => set(() => ({ accessToken })),
  setRefreshToken: (refreshToken) => set(() => ({ refreshToken })),
}));

export { useTokenStore };
