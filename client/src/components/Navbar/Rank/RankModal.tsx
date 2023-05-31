"use client";
import { useEffect, useRef } from "react";
import RankModalProps from "./rankModalProps";
import { useRouter } from "next/navigation";

/** 2023/05/11 - 실시간 검색어 모달창 - by Kadesti */
const RankModal: RankModalProps = ({ rankBind, curRank }) => {
  const [rankModal, setRankModal] = rankBind;
  const lastIdx = curRank.length - 1;

  const modalRef = useRef<HTMLDivElement>(null);

  const router = useRouter();

  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (e.target instanceof HTMLDivElement) return;
      if (!modalRef.current) return;
      if (modalRef.current.contains(e.target)) return;

      // 모달을 닫는 함수
      setRankModal(false);
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, [setRankModal]);

  return (
    <div
      className="flex flex-col p-3 absolute w-44 right-0 top-14 cursor-pointer bg-white shadow-xl rounded-b-lg z-10"
      ref={modalRef}
    >
      {curRank.map((data, idx) => {
        const last = idx === lastIdx ? "" : "mb-2";
        return (
          <div
            className={`flex justify-between ${last} hover:text-main-400 hover:border-b-2 h-7`}
            onClick={() => router.push(`/search?keyword=${data}`)}
            key={idx}
          >
            <div className="text-xl mr-4">{idx + 1}</div>
            <div className="text-xl truncate">{data}</div>
          </div>
        );
      })}
    </div>
  );
};

export default RankModal;
