"use client";

import { useState } from "react";
import { useModalActions } from "@/components/Login/contextAPI/useModal";

import SearchDiv from "@/components/Header/SearchDiv";
import IsLoginSide from "@/components/Header/LoginSide";
// import InputModal from "@/components/Header/InputModal";
import HeaderLogo from "@/components/Header/HeaderLogo";
import SearchSide from "@/components/Header/SearchSide";
import headerArr from "@/components/Header/HeaderArr";
import { useMemberStore } from "@/store/useMemberStore";
import Link from "next/link";
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import { twMerge } from "tailwind-merge";
import { motion } from "framer-motion";
import { usePathname } from "next/navigation";

const inCommunity = ["free", "feedback", "job", "promotion"];

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const pathname = usePathname();
  const { member } = useMemberStore();
  const { leftArr, rightArr } = headerArr();

  /** 2023/05/20 - 검색창 포커스 여부 - by 1-blue */
  const [isFocus, setIsFocus] = useState(false);

  return (
    <header className="bg-bg h-[96px] border-b border-sub-400 flex justify-center tracking-widest z-10 px-8">
      <div className="flex w-full max-w-[1440px] items-center">
        <HeaderLogo />
        {/* <SearchSide array={leftArr} /> */}
        <section className="flex space-x-6">
          <Link href="/" className="relative flex">
            <span
              className={twMerge(
                "text-2xl font-sub transition-colors",
                (pathname === "/" || inCommunity.some((v) => pathname.includes(v))) && "text-main-400",
              )}
            >
              커뮤니티
            </span>
            {(pathname === "/" || inCommunity.some((v) => pathname.includes(v))) && (
              <motion.div
                className="absolute -bottom-4 left-1/2 -translate-x-1/2 w-3 h-3 rounded-full bg-main-300"
                layoutId="header"
              />
            )}
          </Link>
          <Link href="/notice" className="relative flex">
            <span
              className={twMerge(
                "text-2xl font-sub transition-colors",
                pathname.includes("/notice") && "text-main-400",
              )}
            >
              공지사항
            </span>
            {pathname.includes("/notice") && (
              <motion.div
                className="absolute -bottom-4 left-1/2 -translate-x-1/2 w-3 h-3 rounded-full bg-main-300"
                layoutId="header"
              />
            )}
          </Link>
        </section>

        <div className="ml-auto" />

        <section
          className={twMerge("mr-8 border p-2 rounded-md flex space-x-2 transition-all", isFocus && "border-main-400")}
        >
          <MagnifyingGlassIcon className={twMerge("w-7 h-7 transition-all", isFocus && "stroke-main-500")} />
          <input
            type="search"
            className="bg-transparent outline-none placeholder:text-sm"
            onFocus={() => setIsFocus(true)}
            onBlur={() => setIsFocus(false)}
            placeholder="유저 or 게시글 검색"
          />
        </section>

        {member ? (
          <section className="flex space-x-3">
            <Link href="/login" className="text-xl font-sub">
              로그인
            </Link>
            <div className="border border-sub-400" />
            <Link href="/singup" className="text-xl font-sub">
              회원가입
            </Link>
          </section>
        ) : (
          <></>
        )}
        {/* {isLogin ? <IsLoginSide nickState={nickState} /> : <SearchSide array={rightArr} />} */}
      </div>
    </header>
  );
};

export default Header;
