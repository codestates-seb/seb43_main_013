"use client";

import { useState } from "react";
import { useModalActions } from "@/components/Login/contextAPI/useModal";

import SearchDiv from "@/components/Header/SearchDiv";
import IsLoginSide from "@/components/Header/LoginSide";
// import InputModal from "@/components/Header/InputModal";
import HeaderLogo from "@/components/Header/HeaderLogo";
import SearchSide from "@/components/Header/SearchSide";
import headerArr from "@/components/Header/HeaderArr";

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const [isLogin, setIsLogin] = useState(false);
  // const [isLogin, setIsLogin] = useState(true);

  const nickState = useState(false);
  const { leftArr, rightArr } = headerArr();

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <div className="flex w-full max-w-[1440px] items-center">
        <HeaderLogo />
        <SearchSide array={leftArr} />
        <SearchDiv />
        {isLogin ? <IsLoginSide nickState={nickState} /> : <SearchSide array={rightArr} />}
      </div>
      {/* {inputModal && <InputModal setInputModal={setInputModal} />} */}
    </header>
  );
};

export default Header;
