"use client";
import { useState } from "react";
import { Input, SignInput } from "../../components/login";

/** 2023/05/05 - 회원가입 페이지 컴포넌트 - by Kadesti */
const signup = () => {
  const { idValid, pwValid } = useInputValid();
  // const [disabled, setDisabled] = useState("disabled");
  const [disabled, setDisabled] = useState(false);

  return (
    <div className="w-screen flex justify-center mt-16 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6  rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">회원가입</h1>
        <form className="w-full">
          <Input label="아이디" valid={idValid} />
          <Input label="비밀번호" valid={pwValid} />
          <SignInput label="닉네임" />
          <SignInput label="유튜브 주소" />
          <SignInput label="이메일" />
          <SignInput label="휴대폰 번호" />
          <SignInput label="자기소개" />
        </form>
        <button
          disabled={disabled}
          className={`w-4/5 h-16 mb-6 flex justify-center items-center text-3xl rounded-2xl ${
            disabled ? "bg-slate-300 text-white" : "bg-green-400 hover:bg-green-200 hover:text-slate-400"
          }`}
        >
          회원가입
        </button>
      </div>
    </div>
  );
};

export default signup;

/** 2023/05/05 - 로그인 유효성 체크 상태값 - by Kadesti */
const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, pwValid };
};
