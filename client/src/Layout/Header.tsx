"use client";
import React, { useEffect } from "react";
import { useState } from "react";

import SearchDiv from "../components/Header/SearchDiv";
import LoginSide from "../components/Header/LoginSide";
import { useTokenStore } from "@/store/useTokenStore";
import AuthCheck from "@/components/Header/AuthCheck";

import HeaderLogo from "../components/Header/HeaderLogo";
import SearchSide from "../components/Header/SearchSide";
import headerArr from "../components/Header/HeaderArr";

import { Bars3Icon } from "@heroicons/react/24/solid";

interface Header {
  mobileBind: [boolean, React.Dispatch<boolean>];
}

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC<Header> = ({ mobileBind }) => {
  const [isMobileOpen, setMobileOpen] = mobileBind;
  const [hasToken, setHasToken] = useState(false);
  const { accessToken } = useTokenStore();

  useEffect(() => {
    const newAccessToken = localStorage.getItem("accessToken");
    setHasToken(!!newAccessToken);
  }, [accessToken]);

  const nickState = useState(false);
  const { leftArr, rightArr } = headerArr();

  return (
    <header className="bg-white h-[96px] border-b-4 flex justify-center">
      <AuthCheck />
      <div className="flex w-full max-w-[1440px] items-center px-4">
        <HeaderLogo />
        <SearchSide array={leftArr} />
        <SearchDiv />
        {hasToken ? <LoginSide nickState={nickState} /> : <SearchSide array={rightArr} />}
        <button className="md:hidden hover:cursor-pointer">
          <Bars3Icon className="w-11 min-w-[30px] hover:bg-main-300" onClick={() => setMobileOpen(true)} />
        </button>
      </div>
    </header>
  );
};

export default Header;
