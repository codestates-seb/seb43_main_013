"use client";
import { useTokenStore } from "@/store/useTokenStore";
import { serverInstance } from "@/apis";

const AuthCheck = () => {
  const { setAccessToken } = useTokenStore();

  serverInstance.interceptors.response.use(function (config) {
    if (config.data.message === "Access token refreshed") {
      const accessToken = config.headers.Authorization;
      setAccessToken(accessToken);
      localStorage.setItem("accessToken", accessToken);
    }

    return config;
  });
  return null;
};

export default AuthCheck;
