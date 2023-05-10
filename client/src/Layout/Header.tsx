"use client";

import { useState } from "react";
import { useModalActions } from "@/components/Login/contextAPI/useModal";

import CommuTrend from "@/components/Header/CommuTrend";
import SearchDiv from "@/components/Header/SearchDiv";
import { IsLoginSide, NoneLoginSide } from "@/components/Header/LoginSide";
import InputModal from "@/components/Header/InputModal";

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const [isLogin, setIsLogin] = useState(false);
  // const [isLogin, setIsLogin] = useState(true);
  const [inputModal, setInputModal] = useState(false);

  const nickState = useState(false);

  // contextAPI로 setLoginModal 관리가 필요 (헤더에 로그인 클릭시 모달 팝업)
  // const [loginModal, setLoginModal] = useState<boolean>(false);
  const modalActions = useModalActions();

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <div className="flex w-full max-w-[1440px] items-center">
        <h1 className="text-6xl mr-10 cursor-pointer">CC</h1>
        <CommuTrend />
        <SearchDiv setInputModal={setInputModal} />
      </div>
      {isLogin ? <IsLoginSide nickState={nickState} /> : <NoneLoginSide />}
      {inputModal && <InputModal setInputModal={setInputModal} />}
    </header>
  );
};

export default Header;
