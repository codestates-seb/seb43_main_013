"use client";
import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import Link from "next/link";
import { useTokenStore } from "@/store/useTokenStore";
import { useMemberStore } from "@/store/useMemberStore";
import { useRouter } from "next/navigation";
import useCustomToast from "@/hooks/useCustomToast";

// type
interface Props {
  setNickModal: React.Dispatch<boolean>;
}

const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;
/** 2023/05/10 - 닉네임 클릭 후 등장 모달창 - by Kadesti */
const NickModal: React.FC<Props> = ({ setNickModal }) => {
  const toast = useCustomToast();
  const router = useRouter();
  const { setAccessToken } = useTokenStore();
  const { setMember } = useMemberStore();

  const { member } = useMemberStore();
  const [memberLink, setMemberLink] = useState("");

  useEffect(() => {
    if (member) setMemberLink(`/profile/${member.memberId}`);
  }, [member]);

  const logoutEvent = async () => {
    try {
      const Authorization = localStorage.getItem("accessToken");
      const refreshToken = localStorage.getItem("refreshToken");

      await axios.delete(`${baseUrl}/api/logout`, {
        headers: {
          Authorization,
          "Refresh-Token": refreshToken,
        },
      });

      setAccessToken("");
      setMember(null);
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("member");

      toast({ title: "로그아웃되었습니다.\n 로그인 페이지로 이동됩니다.", status: "success" });
      router.push("/login");
    } catch (error) {
      console.error(error);
    }
  };

  const modalRef = useRef<null | HTMLUListElement>(null);

  /** 2023/05/18 - 외부 클릭 시 모달 닫기 - by 1-blue */
  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (e.target instanceof HTMLSpanElement) return;
      if (!modalRef.current) return;
      if (modalRef.current.contains(e.target)) return;

      // 모달을 닫는 함수
      setNickModal(false);
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, [setNickModal]);

  return (
    <ul ref={modalRef} className="w-full p-3 bg-main-400 text-white absolute mt-48 z-10 rounded-xl flex flex-col">
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
