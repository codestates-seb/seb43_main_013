"use client";

import { ChevronDoubleDownIcon } from "@heroicons/react/24/solid";
import Link from "next/link";

/** 2023/05/04 - 전체 잘못된 경로 - by 1-blue */
const LoadingPage = () => (
  <article className="flex flex-col justify-center items-center mt-24 space-y-8 h-[50vh]">
    <section className="flex h-[100px] space-x-4">
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.1s" }}
      >
        N
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.2s" }}
      >
        O
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.3s" }}
      >
        T
      </span>
      <div />
      <div />
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.4s" }}
      >
        F
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.5s" }}
      >
        O
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.6s" }}
      >
        U
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.7s" }}
      >
        N
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.8s" }}
      >
        D
      </span>
    </section>
    <section className="text-center">
      <h1 className="text-3xl whitespace-pre-line">{"존재하지 않는 페이지입니다!\n경로를 다시 확인해주세요!"}</h1>
    </section>
    <ChevronDoubleDownIcon className="w-16 h-16 text-sub-600 animate-bounce" />
    <section className="flex flex-col justify-center space-y-4">
      <Link
        href="/"
        className="text-2xl border-4 border-main-400 text-main-400 transition-colors hover:bg-main-500 hover:text-white hover:border-main-500 rounded-md px-4 py-3"
      >
        메인 페이지로 이동
      </Link>
    </section>
  </article>
);

export default LoadingPage;
