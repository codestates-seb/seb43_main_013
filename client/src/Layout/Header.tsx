"use client";

import { useState } from "react";
import { useModalActions } from "@/components/Login/contextAPI/useModal";

import CommuTrend from "@/components/Header/CommuTrend";
import SearchDiv from "@/components/Header/SearchDiv";
import { IsLoginSide, NoneLoginSide } from "@/components/Header/LoginSide";
// import InputModal from "@/components/Header/InputModal";
import HeaderLogo from "@/components/Header/HeaderLogo";

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const [isLogin, setIsLogin] = useState(false);
  // const [isLogin, setIsLogin] = useState(true);

  const nickState = useState(false);

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <div className="flex w-full max-w-[1440px] items-center">
        <HeaderLogo />
        <CommuTrend />
        <SearchDiv />
        {isLogin ? <IsLoginSide nickState={nickState} /> : <NoneLoginSide />}
      </div>
      {/* {inputModal && <InputModal setInputModal={setInputModal} />} */}
    </header>
  );
};

export default Header;
