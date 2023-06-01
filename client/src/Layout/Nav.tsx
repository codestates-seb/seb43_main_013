"use client";
import NavRoute from "@/components/Navbar/NavRoute";
import RankMenu from "@/components/Navbar/Rank/RankMenu";
import { useEffect, useState } from "react";
import MobileModal from "@/components/Navbar/MobileModal";

interface Nav {
  mobileBind: [boolean, React.Dispatch<boolean>];
}

/** 2023/05/04 - 네브 컴포넌트 - by Kadesti */
const Nav: React.FC<Nav> = ({ mobileBind }) => {
  const [isMobileOpen, setMobileOpen] = mobileBind;
  const [nickName, setNickName] = useState("");
  const [profileSrc, setProfileSrc] = useState("");

  useEffect(() => {
    const memberState = localStorage.getItem("member");
    const name = memberState ? JSON.parse(memberState).nickname : "비회원";
    const profile = memberState ? JSON.parse(memberState).profileImageUrl : false;
    setNickName(name);
    setProfileSrc(profile);
  }, []);

  return (
    <nav className="bg-white md:h-[64px] shadow-xl flex justify-center items-center px-7">
      <div className="hidden md:flex w-full max-w-[1440px] h-full justify-between items-center relative">
        <NavRoute />
        <RankMenu />
      </div>
      {isMobileOpen ? <MobileModal profileSrc={profileSrc} nickName={nickName} setMobileOpen={setMobileOpen} /> : null}
    </nav>
  );
};

export default Nav;
