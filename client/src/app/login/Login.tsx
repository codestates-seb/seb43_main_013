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

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const LoginWindow = () => {
  return (
    <div className=" bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
      <h1 className="text-5xl mb-6">로그인</h1>
      <form className="w-full" onSubmit={() => console.log("실행")}>
        <LoginInput label="아이디" />
        <LoginInput label="비밀번호" />
        <SmallBtn />
        <LoginBtn text="로그인" />
        <OAuthCon />
      </form>
    </div>
  );
};
