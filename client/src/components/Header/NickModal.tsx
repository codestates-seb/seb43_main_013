import React, { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";

const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;
/** 2023/05/10 - 닉네임 클릭 후 등장 모달창 - by Kadesti */
const NickModal = ({ setNickModal }: { setNickModal: React.Dispatch<boolean> }) => {
  const { setAccessToken } = useTokenStore();
  const { setMember } = useMemberStore();

  const { member } = useMemberStore();
  const [memberLink, setMemberLink] = useState("");

  useEffect(() => {
    if (member) setMemberLink(`/profile/${member.memberId}`);
  }, [member]);

  const logoutEvent = async () => {
    const Authorization = localStorage.getItem("accessToken");
    await axios.post(`${baseUrl}/api/logout`, {
      headers: {
        Authorization,
      },
    });

    setAccessToken("");
    setMember(null);
    localStorage.removeItem("accessToken");
    localStorage.removeItem("member");
  };

  return (
    <ul className="w-full p-3 bg-main-400 text-white absolute mt-48 z-10 rounded-xl flex flex-col">
      <Link href={memberLink} className="cursor-pointer hover:text-black text-xl mb-2">
        내 정보
      </Link>
      <Link href="/free/write" className="cursor-pointer hover:text-black text-xl mb-2">
        글쓰기
      </Link>
      <li
        onClick={(e) => {
          e.preventDefault();
          logoutEvent();
        }}
        className="cursor-pointer hover:text-black text-xl mb-2"
      >
        로그아웃
      </li>
    </ul>
  );
};

export default NickModal;
