"use client";

import React from "react";
import { useState } from "react";

import SearchDiv from "../components/Header/SearchDiv";
import LoginSide from "../components/Header/LoginSide";

import HeaderLogo from "../components/Header/HeaderLogo";
import SearchSide from "../components/Header/SearchSide";
import headerArr from "../components/Header/HeaderArr";
import { useTokenStore } from "@/store/useTokenStore";

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const { accessToken } = useTokenStore();

  const nickState = useState(false);
  const { leftArr, rightArr } = headerArr();

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <div className="flex w-full max-w-[1440px] items-center">
        <HeaderLogo />
        <SearchSide array={leftArr} />
        <SearchDiv />
        {accessToken !== "" ? <LoginSide nickState={nickState} /> : <SearchSide array={rightArr} />}
      </div>
    </header>
  );
};

export default Header;
