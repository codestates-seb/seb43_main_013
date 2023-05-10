"use client";
import { useState } from "react";
import { SignInput } from "../../components/Login/LoginModal";

/** 2023/05/05 - 회원가입 페이지 컴포넌트 - by Kadesti */
const signup = () => {
  const { idValid, setIdValid, pwValid, setPWValid } = useInputValid();
  // const [disabled, setDisabled] = useState("disabled");
  const [disabled, setDisabled] = useState(false);
  const [isSubmit, setIsSubmit] = useState(false);

  const label = [
    {
      label: "아이디",
      valid: idValid,
      id: 0,
    },
    {
      label: "비밀번호",
      valid: pwValid,
      id: 1,
    },
    { label: "닉네임", id: 2 },
    { label: "유튜브 주소", id: 3 },
    { label: "이메일", id: 4 },
    { label: "휴대폰 번호", id: 5 },
    { label: "자기소개", id: 6 },
  ];

  const validFunc = () => {
    if (disabled) setDisabled(true);
    else setDisabled(false);

    if (pwValid) setPWValid(true);
    else setPWValid(false);

    setIsSubmit(true);
  };

  return (
    <div className="w-full flex justify-center mt-16 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">회원가입</h1>
        <form className="w-full flex flex-col items-center" onSubmit={validFunc}>
          {label.map((el) => {
            return <SignInput label={el.label} valid={el.valid} isSubmit={isSubmit} key={el.id} />;
          })}
          <div className="w-full h-44 border-2 border-black flex justify-center items-center rounded-lg mt-6 mb-3 text-2xl">
            사진
          </div>
          <button
            disabled={disabled}
            className={`w-4/5 h-16 mt-5 mb-6 flex justify-center items-center text-3xl rounded-2xl ${
              disabled ? "bg-slate-300 text-white" : "bg-green-400 hover:bg-green-200 hover:text-slate-400"
            }`}
          >
            회원가입
          </button>
        </form>
      </div>
    </div>
  );
};

export default signup;

/** 2023/05/05 - 로그인 유효성 체크 상태값 - by Kadesti */
const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, setIdValid, pwValid, setPWValid };
};
