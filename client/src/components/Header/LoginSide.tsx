import React from "react";
import { ChevronUpIcon, ChevronDownIcon, UserCircleIcon } from "../HeaderIcon";
import NickModal from "./NickModal";
import { useEffect, useState } from "react";

/**
 * 2023/05/10 - 로그인 상태의 검색창 우측 메뉴 - by Kadesti
 * @param nickModal 모달 창 상태
 * @param setNickModal 세터함수
 */

const IsLoginSide = ({ nickState }: { nickState: [boolean, React.Dispatch<boolean>] }) => {
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
    <div className="flex flex-row items-center">
      <div
        onClick={() => setNickModal(!nickModal)}
        className="break-keep flex items-center cursor-pointer hover:text-slate-400 mr-2"
      >
        <div className="flex relative">
          <span className="mr-2 text-2xl cursor-pointer">{nickName}</span>
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
      <img src="https://youtu.be/f8IcRLd54v4" className="w-12 cursor-pointer" />

      {/* {profileSrc !== "" ? (
        <img src={profileSrc} className="" />
      ) : (
        <UserCircleIcon className="w-12 cursor-pointer hover:text-slate-400" />
      )} */}
    </div>
  );
};

export default IsLoginSide;
