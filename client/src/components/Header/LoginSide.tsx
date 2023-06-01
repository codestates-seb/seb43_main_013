import React from "react";
import { useEffect, useState } from "react";
import Avatar from "../Avatar";
import NameButton from "./NameButton";

/**
 * 2023/05/10 - 로그인 상태의 검색창 우측 메뉴 - by Kadesti
 * @param nickModal 모달 창 상태
 * @param setNickModal 세터함수
 */
const LoginSide = ({ nickState }: { nickState: [boolean, React.Dispatch<boolean>] }) => {
  const [nickModal, setNickModal] = nickState;
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
    <div className="hidden md:flex items-center">
      <div
        onClick={() => setNickModal(!nickModal)}
        className="break-keep flex items-center cursor-pointer hover:text-slate-400 mr-2 relative"
      >
        <Avatar src={profileSrc || ""} className="mr-2 w-10 h-10" />
        <span className="mr-2 text-2xl cursor-pointer">{nickName}</span>
        <NameButton nickModal={nickModal} />
      </div>
    </div>
  );
};

export default LoginSide;
