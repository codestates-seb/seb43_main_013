"use client";
import NavRoute from "@/components/Navbar/NavRoute";
import RankMenu from "@/components/Navbar/Rank/RankMenu";

/** 2023/05/04 - 네브 컴포넌트 - by Kadesti */
const Nav = () => {
  return (
    <nav className="bg-bg relative h-[64px] shadow-xl flex justify-center items-center border-b border-sub-400 mb-8 z-[2] px-8">
      <div className="flex w-full max-w-[1440px] h-full justify-between items-center relative">
        <NavRoute />
        <RankMenu />
      </div>
    </nav>
  );
};

export default Nav;
