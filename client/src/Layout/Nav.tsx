"use client";
import NavRoute from "@/components/Navbar/NavRoute";
import RankMenu from "@/components/Navbar/Rank/RankMenu";
import { useState } from "react";

/** 2023/05/04 - 네브 컴포넌트 - by Kadesti */
const Nav = () => {
  const [rankModal, setRankModal] = useState(false);
  const currentRank = [
    {
      id: 1,
      content: "해방일지",
    },
    {
      id: 2,
      content: "2",
    },
    {
      id: 3,
      content: "3",
    },
    {
      id: 4,
      content: "4",
    },
    {
      id: 5,
      content: "5",
    },
    {
      id: 6,
      content: "6",
    },
    {
      id: 7,
      content: "7",
    },
    {
      id: 8,
      content: "8",
    },
    {
      id: 9,
      content: "9",
    },
    {
      id: 10,
      content: "10",
    },
  ];

  return (
    <nav className="bg-white relative h-[64px] shadow-xl flex justify-center items-center px-7">
      <div className="flex w-full max-w-[1440px] h-full justify-between items-center relative">
        <NavRoute />
        <RankMenu />
      </div>
    </nav>
  );
};

export default Nav;
