"use client";
import axios from "axios";
import { serverInstance } from "@/apis";
import { useCallback, useEffect } from "react";
import { useTokenStore } from "@/store/useTokenStore";
import { useRouter } from "next/navigation";

const AuthCheck = () => {
  const router = useRouter();
  const { refreshToken, setAccessToken } = useTokenStore();

  serverInstance.interceptors.response.use(function (value) {
    // 1. 엑세스 토큰과 리프레시 토큰 모두 유효할 경우 - 생략

    // 2. 엑세스 토큰이 만료된 경우
    // 3. 둘 다 만료된 경우
    // const refreshProcess = (router: any) => {
    //   axios
    //     .post("/refresh-token", {
    //       headers: {
    //         refreshToken,
    //       },
    //     })
    //     .then((res) => setAccessToken(res.data))
    //     // 3. 둘 다 만료된 경우
    //     .catch(() => router.push("/login"));
    // };

    // console.log("value: ", value);
    // console.log("value.status: ", value.status);
    // console.log("value.statusText: ", value.statusText);

    // console.log("value.status: ", value.status);

    useEffect(() => {
      console.log("value: ", value);
      // 2. 엑세스 토큰이 만료된 경우
      if (value.status === 401 || value.statusText === "unauthenticated") {
        // refreshProcess(router);
      }
      if (value.status === 200) console.log("실행");
      // if (value.status === 200) console.log("value: ", value);
    }, []);
    // }, [value.status]);

    return value;
  });

  return null;
};

export default AuthCheck;
