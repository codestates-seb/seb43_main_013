import React from "react";
import { ChevronUpIcon, ChevronDownIcon } from "../HeaderIcon";
import NickModal from "./NickModal";
import { useEffect, useState } from "react";
import Avatar from "../Avatar";

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
    const nickname = memberState ? JSON.parse(memberState).name : "비회원";
    const profile = memberState ? JSON.parse(memberState).profileImageUrl : false;
    setNickName(nickname);
    setProfileSrc(profile);
  }, []);

  return (
    <div className="flex items-center">
      <div
        onClick={() => setNickModal(!nickModal)}
        className="break-keep flex items-center cursor-pointer hover:text-slate-400 mr-2 relative"
      >
        <Avatar src={profileSrc || ""} className="mr-2 w-10 h-10" />
        <span className="mr-2 text-2xl cursor-pointer whitespace-nowrap">
          {nickName.slice(0, 8)}
          {nickName.length >= 9 && "..."}
        </span>
        {nickModal ? (
          <>
            <NickModal setNickModal={setNickModal} />
            <ChevronUpIcon className="w-7 cursor-pointer" />
          </>
        ) : (
          <ChevronDownIcon className="w-7 cursor-pointer" />
        )}
      </div>
    </div>
  );
};

export default LoginSide;
