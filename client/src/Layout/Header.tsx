"use client";

import React from "react";
import { useState } from "react";

import SearchDiv from "../components/Header/SearchDiv";
import IsLoginSide from "../components/Header/LoginSide";

import HeaderLogo from "../components/Header/HeaderLogo";
import SearchSide from "../components/Header/SearchSide";
import headerArr from "../components/Header/HeaderArr";
import { useMemberStore } from "@/store/useMemberStore";

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const { member } = useMemberStore();

  const nickState = useState(false);
  const { leftArr, rightArr } = headerArr();

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <div className="flex w-full max-w-[1440px] items-center">
        <HeaderLogo />
        <SearchSide array={leftArr} />
        <SearchDiv />
        {member ? <IsLoginSide nickState={nickState} /> : <SearchSide array={rightArr} />}
      </div>
    </header>
  );
};

export default Header;
