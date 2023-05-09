"use client";

import Link from "next/link";
import { useState } from "react";

import { Input, OAuthCon, SmallBtn } from "../../src/components/login";

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  // const [isLogin, setIsLogin] = useState(false);
  const [isLogin, setIsLogin] = useState(false);
  const [inputModal, setInputModal] = useState(false);
  const [nickModal, setNickModal] = useState(false);
  // const [loginModal, setLoginModal] = useState(false);
  const [loginModal, setLoginModal] = useState<boolean>(false);

  const { idValid, pwValid } = useInputValid();

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <div className="flex w-full max-w-5xl items-center">
        <h1 className="text-6xl mr-10 cursor-pointer">CC</h1>
        <div className="flex flex-row mr-10">
          <Link href="/">
            <h3 className="text-2xl mr-5 break-keep cursor-pointer text-black hover:text-rose-400">커뮤니티</h3>
          </Link>
          <h3 className="text-2xl break-keep cursor-pointer text-black hover:text-rose-400">트렌드</h3>
        </div>
        <div className="bg-slate-400 w-full flex justify-end mr-10 rounded-xl h-8 items-center px-3 cursor-pointer">
          <MagnifyingGlass />
        </div>
      </div>
      {isLogin ? (
        <div className="flex flex-row pr-5 items-center">
          <div
            onClick={() => setNickModal(!nickModal)}
            className="break-keep flex items-center mr-5 cursor-pointer text-black hover:text-slate-400"
          >
            <div className="flex flex-col">
              <span className="mr-2 text-xl">닉네임</span>
            </div>
            {nickModal ? <ChevronUp /> : <ChevronDown />}
          </div>
          <UserCircle />
        </div>
      ) : (
        <div className="flex items-center">
          <h3
            onClick={() => {
              setLoginModal(true);
            }}
            className="text-2xl mr-5 break-keep cursor-pointer text-black hover:text-rose-400"
          >
            로그인
          </h3>
          <h3 className="text-2xl break-keep cursor-pointer text-black hover:text-rose-400">회원가입</h3>
        </div>
      )}
      {nickModal && (
        <>
          <div className="w-1/6 p-3 bg-slate-400 text-white absolute mt-20 right-20 z-10 rounded-xl">
            {["내 정보", "글쓰기", "로그아웃"].map((text, i) => (
              <div className="cursor-pointer hover:text-slate-200 text-xl mb-2" key={i}>
                {text}
              </div>
            ))}
          </div>
          <div className="bg-none absolute w-full h-full" onClick={() => setNickModal(false)}>
            1
          </div>
        </>
      )}
      {inputModal && (
        <div className="absolute bottom-0 bg-black/20 w-screen h-screen flex justify-center">
          <div>1</div>
        </div>
      )}
      {loginModal && <LoginWindow setLoginModal={setLoginModal} />};
    </header>
  );
};

export default Header;

/** 2023/05/04 - 돋보기 아이콘 - by Kadesti */
const MagnifyingGlass = () => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth={1.5}
      stroke="currentColor"
      className="w-6 h-6  text-black hover:text-slate-400"
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"
      />
    </svg>
  );
};

/** 2023/05/04 - 유저 프로필 아이콘 - by Kadesti */
const UserCircle = () => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth={1}
      stroke="currentColor"
      className="w-12 h-12 cursor-pointer text-black hover:text-slate-400"
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M17.982 18.725A7.488 7.488 0 0012 15.75a7.488 7.488 0 00-5.982 2.975m11.963 0a9 9 0 10-11.963 0m11.963 0A8.966 8.966 0 0112 21a8.966 8.966 0 01-5.982-2.275M15 9.75a3 3 0 11-6 0 3 3 0 016 0z"
      />
    </svg>
  );
};

/** 2023/05/04 - 아래 화살표 아이콘 - by Kadesti */
const ChevronDown = () => {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-6 h-6 cursor-pointer">
      <path
        fillRule="evenodd"
        d="M12.53 16.28a.75.75 0 01-1.06 0l-7.5-7.5a.75.75 0 011.06-1.06L12 14.69l6.97-6.97a.75.75 0 111.06 1.06l-7.5 7.5z"
        clipRule="evenodd"
      />
    </svg>
  );
};

/** 2023/05/04 - 위 화살표 아이콘 - by Kadesti */
const ChevronUp = () => {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="w-6 h-6">
      <path
        fillRule="evenodd"
        d="M11.47 7.72a.75.75 0 011.06 0l7.5 7.5a.75.75 0 11-1.06 1.06L12 9.31l-6.97 6.97a.75.75 0 01-1.06-1.06l7.5-7.5z"
        clipRule="evenodd"
      />
    </svg>
  );
};

/** 2023/05/05 - 로그인 유효성 체크 상태값 - by Kadesti */
const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, pwValid };
};

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const LoginWindow = ({ setLoginModal }: { setLoginModal: Function }) => {
  const { idValid, pwValid } = useInputValid();

  return (
    <div className="fixed w-screen flex justify-center items-center z-10">
      <div
        onClick={(e) => {
          e.preventDefault();
          setLoginModal(false);
        }}
        className="bg-black/50 w-screen h-screen hover:cursor-pointer"
      />
      <div className="fixed bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">로그인</h1>
        <form className="w-full">
          <Input label="아이디" valid={idValid} />
          <Input label="비밀번호" valid={pwValid} />
        </form>
        <SmallBtn />
        <OAuthCon />
      </div>
    </div>
  );
};
