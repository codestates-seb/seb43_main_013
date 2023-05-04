import axios from "axios";

/** 2023/05/04 - 백엔드에 API 요청하는 axios 인스턴스 - by 1-blue */
export const serverInstance = axios.create({
  baseURL: process.env.BASE_URL,
  withCredentials: true,
  timeout: 5000,
});
