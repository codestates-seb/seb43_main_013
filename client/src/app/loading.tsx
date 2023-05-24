"use client";

import Link from "next/link";
import { ChevronDoubleDownIcon } from "@heroicons/react/24/solid";

/** 2023/05/04 - 전체 잘못된 경로 - by 1-blue */
const NotFoundPage = () => (
  <article className="flex flex-col justify-center items-center mt-24 space-y-8 h-[50vh]">
    <section className="flex h-[100px] space-x-4">
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.1s" }}
      >
        L
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
        A
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.4s" }}
      >
        D
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.5s" }}
      >
        I
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.6s" }}
      >
        N
      </span>
      <span
        className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-not-found-text-bounce text-main-500"
        style={{ animationDelay: "0.7s" }}
      >
        G
      </span>
    </section>
    <section className="flex flex-col justify-center items-center">
      <h1 className="text-3xl whitespace-pre-line">{"로딩중입니다..."}</h1>
      <ChevronDoubleDownIcon className="w-16 h-16 text-sub-600 animate-bounce" />
      <section className="flex flex-col justify-center space-y-4">
        <Link
          href="/"
          className="text-2xl border-4 border-main-400 text-main-400 transition-colors hover:bg-main-500 hover:text-white hover:border-main-500 rounded-md px-4 py-3"
        >
          메인 페이지로 이동
        </Link>
      </section>
    </section>
  </article>
);

export default NotFoundPage;
