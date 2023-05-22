"use client";

import { useState } from "react";
import HeaderLogo from "@/components/Header/HeaderLogo";
import { useMemberStore } from "@/store/useMemberStore";
import Link from "next/link";
import {
  MagnifyingGlassIcon,
  ChevronDownIcon,
  UserCircleIcon as OUserCircleIcon,
  PencilIcon as OPencilIcon,
  ArrowLeftOnRectangleIcon as OArrowLeftOnRectangleIcon,
} from "@heroicons/react/24/outline";
import {
  UserCircleIcon as SUserCircleIcon,
  PencilIcon as SPencilIcon,
  ArrowLeftOnRectangleIcon as SArrowLeftOnRectangleIcon,
} from "@heroicons/react/24/solid";
import { twMerge } from "tailwind-merge";
import { motion } from "framer-motion";
import { usePathname } from "next/navigation";
import useLogInModal from "@/store/useLogInStore";
import useSignUpStore from "@/store/useSignUpStore";
import Avatar from "@/components/Avatar";
import InfoDropdown from "@/components/InfoDropdown";

const inCommunity = ["free", "feedback", "job", "promotion"];

/** 2023/05/04 - 헤더 컴포넌트 - by Kadesti */
const Header: React.FC = () => {
  const pathname = usePathname();
  const { member } = useMemberStore();

  /** 2023/05/20 - 로그인 모달 상태 - by 1-blue */
  const { openLogInModal } = useLogInModal();

  /** 2023/05/20 - 회원가입 모달 상태 - by 1-blue */
  const { openSignUpModal } = useSignUpStore();

  /** 2023/05/20 - 검색창 포커스 여부 - by 1-blue */
  const [isFocus, setIsFocus] = useState(false);

  return (
    <header className="bg-bg h-[96px] border-b border-sub-400 flex justify-center tracking-widest z-10 px-8 font-main">
      <div className="flex w-full max-w-[1440px] items-center">
        <HeaderLogo />
        {/* <SearchSide array={leftArr} /> */}
        <section className="flex space-x-6">
          <Link href="/" className="relative flex">
            <span
              className={twMerge(
                "text-2xl transition-colors",
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
            <span className={twMerge("text-2xl transition-colors", pathname.includes("/notice") && "text-main-400")}>
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
          className={twMerge(
            "mr-8 border p-2 rounded-md flex items-center space-x-2 transition-all",
            isFocus && "border-main-400",
          )}
        >
          <MagnifyingGlassIcon className={twMerge("w-5 h-5 transition-all", isFocus && "stroke-main-500")} />
          <input
            type="search"
            className="bg-transparent outline-none placeholder:text-sm"
            onFocus={() => setIsFocus(true)}
            onBlur={() => setIsFocus(false)}
            placeholder="ex) 먹방"
          />
        </section>

        {member ? (
          <section className="relative flex items-center space-x-3">
            <InfoDropdown nickname={member.nickname} memberId={member.memberId} />
            <Avatar src={member.profileImageUrl} className="w-12 h-12" />
          </section>
        ) : (
          <section className="flex space-x-3">
            <button type="button" onClick={openLogInModal} className="text-xl">
              로그인
            </button>
            <div className="border border-sub-400" />
            <button type="button" onClick={openSignUpModal} className="text-xl">
              회원가입
            </button>
          </section>
        )}
      </div>
    </header>
  );
};

export default Header;
