"use client";

import Link from "next/link";
import { motion } from "framer-motion";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  type: string;
  memberId: number;
}

/** 2023/05/17 - 네비게이션 - by 1-blue */
const getNavs = (memberId: number) => [
  { type: "written", text: "내 게시판들", href: `/profile/${memberId}?type=written&page=1` },
  { type: "bookmarked", text: "북마크 게시판들", href: `/profile/${memberId}?type=bookmarked&page=1` },
  { type: "liked", text: "좋아요 게시판들", href: `/profile/${memberId}?type=liked&page=1` },
];

/** 2023/05/17 - 프로필 네비게이션 - by 1-blue */
const ProfileNav: React.FC<Props> = ({ type, memberId }) => {
  return (
    <nav className="flex flex-col p-6 space-y-1 bg-white shadow-2xl m-4 rounded-lg">
      {getNavs(memberId).map((nav) => (
        <Link
          key={nav.type}
          href={nav.href}
          className={twMerge(
            "group flex justify-between items-center p-2 rounded-lg text-sub-500 font-bold transition-colors hover:text-main-600",
            type === nav.type && "text-main-500",
          )}
        >
          <span>{nav.text}</span>
          {type === nav.type && (
            <motion.div
              layoutId="nav"
              className="w-3 h-3 rounded-full bg-main-500 transition-colors group-hover:bg-main-600"
            />
          )}
        </Link>
      ))}
    </nav>
  );
};

export default ProfileNav;
