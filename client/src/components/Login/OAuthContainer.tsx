"use client";

import Link from "next/link";
import { linkArr, linkText } from "./linkList";
import google from "/src/public/images/google.svg";
import naver from "/src/public/images/naver.svg";
import kakao from "/src/public/images/kakao.svg";
import Image from "next/image";

const OAuthContainer = () => {
  const svgArr = [google, naver, kakao];
  const commonStyle =
    "border-2 w-full h-16 mb-4 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50";

  const linkcolor = (linkText: string) => {
    if (linkText === "구글 로그인") return "rose-600";
    if (linkText === "네이버 로그인") return "green-600";
    if (linkText === "카카오 로그인") return "yellow-300";
  };

  return (
    <div className="w-full flex flex-col">
      {linkArr.map((link, idx) => {
        const linkColor = linkcolor(linkText[idx]);
        return (
          <Link href={link} className={`border-${linkColor} ${commonStyle}`} key={idx}>
            <div className={`flex items-center text-2xl text-${linkColor}`}>
              <Image src={svgArr[idx]} alt="" key={idx} width={60} />
              {linkText[idx]}
            </div>
          </Link>
        );
      })}

      <Link href="/signup" className={`border-main-500 ${commonStyle} text-main-500`}>
        회원가입
      </Link>
    </div>
  );
};

export default OAuthContainer;
