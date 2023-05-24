"use client";
import axios from "axios";
import { serverInstance } from "@/apis";
import { useCallback, useEffect } from "react";
import { useTokenStore } from "@/store/useTokenStore";
import { useRouter } from "next/navigation";

const AuthCheck = () => {
  const { refreshToken, setAccessToken } = useTokenStore();

  serverInstance.interceptors.response.use(function (value) {
    // 1. 엑세스 토큰과 리프레시 토큰 모두 유효할 경우 - 생략

    // 2. 엑세스 토큰이 만료된 경우
    // 3. 둘 다 만료된 경우
    // const refreshProcess = useCallback((router: any) => {
    //   axios
    //     .post("/refresh-token", {
    //       headers: {
    //         refreshToken,
    //       },
    //     })
    //     .then((res) => setAccessToken(res.data))
    //     // 3. 둘 다 만료된 경우
    //     .catch(() => router.push("/login"));
    // }, []);

    // console.log("value: ", value);

    useEffect(() => {
      const router = useRouter();

      // 2. 엑세스 토큰이 만료된 경우
      // if (value.status === 401 || value.statusText === "unauthenticated") {
      //   refreshProcess(router);
      // }
    }, [value.status]);

    return value;
  });

  return null;
};

export default AuthCheck;
