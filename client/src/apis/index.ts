import axios from "axios";

/** 2023/05/04 - 백엔드에 API 요청하는 axios 인스턴스 - by 1-blue */
export const serverInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL + "/api",
  withCredentials: true,
  timeout: 5000,
});

// FIXME: 임시 추가
serverInstance.interceptors.request.use(function (config) {
  const accessToken = localStorage.getItem("accessToken");
  const refreshToken = localStorage.getItem("refreshToken");

  if (accessToken) {
    config.headers.Authorization = `${accessToken}`;
  }
  if (refreshToken) {
    // config.headers.refresh = refreshToken;
  }

  return config;
});

export * from "./categories";
export * from "./board";
export * from "./comment";
export * from "./recomment";
export * from "./bookmark";
export * from "./like";
export * from "./member";
export * from "./follow";
export * from "./followers";
export * from "./followings";
export * from "./auth";
export * from "./youtubeList";
