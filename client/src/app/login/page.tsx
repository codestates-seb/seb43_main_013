"use client";
import { useState } from "react";
import { Input, OAuthCon } from "../../components/login";

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const login = () => {
  const { idValid, pwValid } = useInputValid();

  return (
    <div className="w-screen flex justify-center mt-16 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6  rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">로그인</h1>
        <form className="w-full">
          <Input label="아이디" valid={idValid} />
          <Input label="비밀번호" valid={pwValid} />
        </form>
        <OAuthCon />
      </div>
    </div>
  );
};

export default login;

/** 2023/05/05 - 로그인 유효성 체크 상태값 - by Kadesti */
const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, pwValid };
};
