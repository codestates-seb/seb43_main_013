"use client";

import Link from "next/link";
import { motion } from "framer-motion";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  type: string;
  memberId: number;
}

/** 2023/05/15 - 탭 - by 1-blue */
const getTabs = (memberId: number) => [
  { type: "board", text: "내 게시글들", href: `/profile/${memberId}?type=board&page=1` },
  { type: "bookmark", text: "내 북마크 게시글들", href: `/profile/${memberId}?type=bookmark&page=1` },
];

/** 2023/05/15 - 프로필 탭 & 해당 유저의 게시글들 - by 1-blue */
const ProfileTab: React.FC<Props> = ({ type, memberId }) => {
  return (
    <section className="flex-1 border-r px-4 py-2 space-y-2 bg-white shadow-2xl m-4 rounded-lg">
      {/* 내 게시글 / 북마크한 게시글 라우팅 */}
      <ul className="flex">
        {getTabs(memberId).map((tab) => (
          <Link key={tab.type} href={tab.href} className="relative flex-1 flex flex-col items-center border-b-2">
            <span
              className={twMerge(
                "rounded-t-md py-2 w-full text-center",
                tab.type === type && "text-main-400 font-bold transition-colors bg-main-100",
              )}
            >
              {tab.text}
            </span>
            {tab.type === type && (
              <motion.div className="absolute -bottom-0.5 z-[1] w-full h-0.5 rounded-full bg-main-400" layoutId="tab" />
            )}
          </Link>
        ))}
      </ul>

      {/* FIXME: 현재 타입의 게시글들 + 페이지네이션 */}
      {/* <ul className="flex flex-col space-y-4">
        {Array(40)
          .fill(null)
          .map((v, i) => (
            <li key={i}>대충 게시글들</li>
          ))}
      </ul> */}
    </section>
  );
};

export default ProfileTab;
