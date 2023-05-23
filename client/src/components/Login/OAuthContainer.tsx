"use client";

import Link from "next/link";
import { linkArr, linkText } from "./linkList";
import google from "/src/public/images/google.svg";
import naver from "/src/public/images/naver.svg";
import kakao from "/src/public/images/kakao.svg";
import Image from "next/image";

const OAuthContainer = () => {
  const svgArr = [google, naver, kakao];

  const bgcolor = (linkText: string) => {
    if (linkText === "구글 로그인") return "bg-white border-2 border-black";
    if (linkText === "네이버 로그인") return "bg-green-600";
    if (linkText === "카카오 로그인") return "bg-yellow-300";
  };

  return (
    <div className="w-full flex flex-col">
      {linkArr.map((link, idx) => {
        const backColor = bgcolor(linkText[idx]);
        return (
          <Link
            href={link}
            className={`${backColor} text-white w-full h-16 mb-4 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50`}
            key={idx}
          >
            <div
              className={`flex items-center text-2xl ${
                linkText[idx] === "네이버 로그인" ? "text-white" : "text-black"
              }`}
            >
              <Image src={svgArr[idx]} alt="" key={idx} width={60} />
              {linkText[idx]}
            </div>
          </Link>
        );
      })}
    </div>
  );
};

export default OAuthContainer;
