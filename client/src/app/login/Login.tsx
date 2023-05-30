"use client";
const Login = () => {
  return (
    <div className="w-full flex justify-center mt-12">
      <LoginWindow />
    </div>
  );
};

export default Login;

import LoginBtn from "@/components/Login/Button/LoginBtn";
import LoginInput from "@/components/Login/LoginInput";
import { useState } from "react";
import axios from "axios";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";
import { useRouter } from "next/navigation";
import { useLoadingStore } from "@/store";
import useCustomToast from "@/hooks/useCustomToast";
import OAuthContainer from "@/components/Login/OAuthContainer";

const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const LoginWindow = () => {
  const router = useRouter();
  const toast = useCustomToast();
  const { loading } = useLoadingStore();

  const [emailData, setEmailData] = useState("");
  const [password, setPassword] = useState("");
  const { setAccessToken, setRefreshToken } = useTokenStore();
  const { setMember } = useMemberStore();

  const [submitCnt, setsubmitCnt] = useState(0);
  const onsubmit = async () => {
    try {
      loading.start();

      setsubmitCnt((prev: number) => prev + 1);
      const data = { username: emailData, password };
      const response = await axios.post(`${baseUrl}/api/login`, data);

      /** 2023/05/13 - 응답의 토큰과 데이터를 전역상태로 저장 - by Kadesti */
      const authorization = response.headers.authorization;
      const refreshtoken = response.headers["refresh-token"];

      setMember(response.data);
      setAccessToken(authorization);
      setRefreshToken(refreshtoken);

      localStorage.setItem("accessToken", authorization);
      localStorage.setItem("refreshToken", refreshtoken);
      localStorage.setItem("member", JSON.stringify(response.data));

      toast({ title: "로그인에 성공했습니다.\n메인 페이지로 이동됩니다.", status: "success" });

      return router.replace("/");
    } catch (error) {
      console.error(error);

      toast({ title: "로그인에 실패했습니다.", status: "error" });
    } finally {
      loading.end();
    }
  };

  return (
    <div className="bg-white w-2/5 min-w-[450px] max-w-[600px] flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
      <h1 className="text-4xl">로그인</h1>
      <form
        className="w-full"
        onSubmit={(e) => {
          e.preventDefault();
          onsubmit();
        }}
      >
        <LoginInput label="이메일" value={emailData} setValue={setEmailData} submitCnt={submitCnt} />
        <LoginInput label="비밀번호" value={password} setValue={setPassword} submitCnt={submitCnt} />
        <LoginBtn text="로그인" />
      </form>
      <AddLogin />
    </div>
  );
};

const AddLogin = () => {
  return (
    <>
      <div className="text-3xl my-3">또는</div>
      <OAuthContainer />
    </>
  );
};
