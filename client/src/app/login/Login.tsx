"use client";
const Login = () => {
  return (
    <div className="w-full h-screen flex justify-center items-center">
      <LoginWindow />
    </div>
  );
};

export default Login;

import SmallBtn from "@/components/Login/Button/SmallBtn";
import LoginBtn from "@/components/Login/Button/LoginBtn";
import OAuthCon from "@/components/Login/Container/OAuthCon";
import LoginInput from "@/components/Login/LoginInput";
import { useState } from "react";
import axios from "axios";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";

const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const LoginWindow = () => {
  const [emailData, setEmailData] = useState("");
  const [password, setPassword] = useState("");
  const { setAccessToken, setRefreshToken } = useTokenStore();
  const { setMember } = useMemberStore();

  const onsubmit = async () => {
    try {
      const data = { username: emailData, password };
      const response = await axios.post(`${baseUrl}/api/login`, data);

      /** 2023/05/13 - 응답의 토큰과 데이터를 전역상태로 저장 - by Kadesti */
      const { authorization, refreshtoken } = response.headers;
      setMember(response.data);

      setAccessToken(authorization);
      setRefreshToken(refreshtoken);

      localStorage.setItem("accessToken", authorization);
      localStorage.setItem("refreshToken", refreshtoken);
    } catch (error) {
      alert(error);
    }
  };

  return (
    <div className=" bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
      <h1 className="text-5xl mb-6">로그인</h1>
      <form
        className="w-full"
        onSubmit={(e) => {
          e.preventDefault();
          onsubmit();
        }}
      >
        <LoginInput label="이메일" value={emailData} setValue={setEmailData} />
        <LoginInput label="비밀번호" value={password} setValue={setPassword} />
        <SmallBtn />
        <LoginBtn text="로그인" />
        <OAuthCon />
      </form>
    </div>
  );
};
