"use client";

import Link from "next/link";
import { ChevronDoubleDownIcon } from "@heroicons/react/24/solid";

// type
interface Props {
  error: Error;
  reset: () => void;
}

/** 2023/05/04 - Erorr 페이지 - by 1-blue */
const Error: React.FC<Props> = ({ error, reset }) => {
  return (
    <article className="flex flex-col justify-center items-center mt-24 space-y-8">
      <section className="h-[100px] space-x-4">
        <span
          className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-error-text-bounce text-red-500"
          style={{ animationDelay: "0.1s" }}
        >
          E
        </span>
        <span
          className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-error-text-bounce text-red-500"
          style={{ animationDelay: "0.2s" }}
        >
          R
        </span>
        <span
          className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-error-text-bounce text-red-500"
          style={{ animationDelay: "0.3s" }}
        >
          R
        </span>
        <span
          className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-error-text-bounce text-red-500"
          style={{ animationDelay: "0.4s" }}
        >
          O
        </span>
        <span
          className="relative top-5 inline-block text-[48px] sm:text-[60px] md:text-[80px] font-bold font-special animate-error-text-bounce text-red-500"
          style={{ animationDelay: "0.5s" }}
        >
          R
        </span>
      </section>
      <section className="text-center">
        <h1 className="text-3xl whitespace-pre-line">{"문제가 발생했습니다.\n잠시후에 다시 시도해주세요!"}</h1>
        <p className="text-lg">( {error?.message} )</p>
      </section>
      <ChevronDoubleDownIcon className="w-16 h-16 text-sub-600 animate-bounce" />
      <section className="flex flex-col justify-center space-y-4">
        <button
          type="button"
          onClick={reset}
          className="text-2xl border-4 border-main-400 text-main-400 transition-colors hover:bg-main-500 hover:text-white hover:border-main-500 rounded-md px-4 py-3"
        >
          새로고침
        </button>
        <Link
          href="/"
          className="text-2xl border-4 border-main-400 text-main-400 transition-colors hover:bg-main-500 hover:text-white hover:border-main-500 rounded-md px-4 py-3"
        >
          메인 페이지로 이동
        </Link>
      </section>
    </article>
  );
};

export default Error;
