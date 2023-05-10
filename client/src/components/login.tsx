"use client";
import Link from "next/link";
import { useState } from "react";

/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const Input = ({ label, valid }: { label: "아이디" | "비밀번호"; valid: boolean }) => {
  return (
    <div className="w-full h-28">
      <div className="text-xl flex mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
      />
      {valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </div>
  );
};

/** 2023/05/05 - 회원가입 인풋 창 - by Kadesti */
const SignInput = ({ label, valid, isSubmit }: { label: string; valid?: boolean; isSubmit?: boolean }) => {
  return (
    <div className="w-full h-28">
      <div className="flex text-xl mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
      />
      {!valid && isSubmit && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </div>
  );
};

/** 2023/05/10 - 로그인 버튼 컨테이너  - by Kadesti */
const OAuthCon = () => {
  // const btnText: string[] = ["OAuth", "OAuth"];
  const btnText: string[] = new Array(2).fill("OAuth");

  return (
    <>
      {btnText.map((text, idx) => (
        <LoginBtn text={text} key={idx} />
      ))}
      <button className="bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </button>
    </>
  );
};

/** 2023/05/10 - 로그인, OAuth 버튼 - by Kadesti */
const LoginBtn = ({ text }: { text: string }) => {
  return (
    <button className="mb-6 bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
      {text}
    </button>
  );
};

/** 2023/05/08 - 회원가입 비번 찾기 버튼 - by Kadesti */
const SmallBtn = () => {
  return (
    <div className="flex justify-end">
      <Link
        href="/signup"
        className="mb-6 bg-green-400 p-1 mr-2 flex justify-center items-center text-lg rounded-lg cursor-pointer hover:bg-green-200 hover:text-slate-400"
      >
        회원가입
      </Link>
      <Link
        href="/signup"
        className="mb-6 bg-green-400 p-1 flex justify-center items-center text-lg rounded-lg cursor-pointer hover:bg-green-200 hover:text-slate-400"
      >
        비밀번호 찾기
      </Link>
    </div>
  );
};

/** 2023/05/05 - 로그인 유효성 체크 상태값 - by Kadesti */
const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, setIdValid, pwValid, setPWValid };
};

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const LoginWindow = () => {
  const { idValid, pwValid } = useInputValid();

  return (
    <div className="fixed w-screen flex justify-center items-center">
      <div className="fixed bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">로그인</h1>
        <form className="w-full">
          <Input label="아이디" valid={idValid} />
          <Input label="비밀번호" valid={pwValid} />
          <SmallBtn />
          <LoginBtn text="로그인" />
          <OAuthCon />
        </form>
      </div>
    </div>
  );
};

/** 2023/05/10 - 최상단에 띄울 Modal 컴포넌트 - by Kadesti */
const Modal = () => {
  // contextAPI로 setLoginModal 관리가 필요 (헤더에 로그인 클릭시 모달 팝업)
  const [loginModal, setLoginModal] = useState(false);

  if (loginModal) {
    return (
      <>
        <div id="backdrop-root">
          <div id="overlay" className="w-screen h-screen absolute bg-black/20 z-10" />
        </div>
        <div id="modal-root" className="w-screen h-screen absolute flex justify-center items-center z-20">
          <LoginWindow />
        </div>
      </>
    );
  }

  return <></>;
};

export { Input, OAuthCon, SignInput, SmallBtn, Modal };
