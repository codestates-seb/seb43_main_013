"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

/** 2023/05/11 - 게시글 자체의 네비게이션 - by 1-blue */
const Navigation = () => {
  const pathname = usePathname();

  const lastIndex = pathname?.lastIndexOf("/");
  const href = pathname?.slice(0, lastIndex);

  return (
    <article className="p-8 flex justify-between bg-white shadow-black/40 shadow-sm m-4 rounded-md">
      <Link
        href={href || ""}
        className="flex justify-center items-center bg-main-400 text-white px-3 py-2 rounded-sm text-sm transition-colors hover:bg-main-500 active:bg-main-600  focus:outline-none focus:bg-main-500 focus:ring-2 focus:ring-main-500 focus:ring-offset-2"
      >
        목록으로
      </Link>
      <Link
        href=""
        className="flex justify-center ml-auto px-3 py-2 rounded-sm text-sm text-main-400 bg-white border-2 border-main-400 transition-colors hover:border-main-500 hover:text-main-500 active:border-main-600 active:text-main-600  focus:outline-none focus:border-main-500 focus:text-main-600"
      >
        이전 글
      </Link>
      <Link
        href=""
        className="flex justify-center ml-4 px-3 py-2 rounded-sm text-sm text-main-400 bg-white border-2 border-main-400 transition-colors hover:border-main-500 hover:text-main-500 active:border-main-600 active:text-main-600  focus:outline-none focus:border-main-500 focus:text-main-600"
      >
        다음 글
      </Link>
    </article>
  );
};

export default Navigation;
