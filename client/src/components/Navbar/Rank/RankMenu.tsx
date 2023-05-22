"use client";
import { useEffect, useState, useRef } from "react";
import RankModal from "./RankModal";
import RankClose from "./RankClose";
import Ticker from "@/components/Ticker";
import { ChevronDownIcon, ChevronUpIcon } from "@heroicons/react/24/outline";
import Link from "next/link";

/** FIXME: https://newmainproject.gitbook.io/main-project/undefined-2/undefined-1 */
const rankData = {
  data: ["랍스터", "맨유", "민재", "MSI", "뷰티", "음악", "대충", "인기", "검색어"],
  pageInfo: {
    page: 1,
    size: 10,
    totalElements: 9,
    totalPages: 1,
  },
};

/** 2023/05/11 - 실시간 검색어 순위 - by Kadesti */
const RankMenu = () => {
  const [showRank, setShowRank] = useState(false);

  const modalRef = useRef<HTMLUListElement>(null);

  /** 2023/04/25 - 외부 클릭 시 모달 닫기 - by 1-blue */
  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (!modalRef.current) return;
      // 위쪽은 부가적인 부분이라 수정/삭제를 해도 되고 아래 부분이 핵심
      // 현재 클릭한 엘리먼트가 모달의 내부에 존재하는 엘리먼트인지 확인
      if (modalRef.current.contains(e.target)) return;

      // 모달을 닫는 함수
      setShowRank(false);
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, []);

  return (
    <section className="relative">
      <button type="button" className="flex items-center space-x-2" onClick={() => setShowRank(true)}>
        <Ticker lists={rankData.data} />
        <ChevronDownIcon className="w-4 h-4 stroke-2" />
      </button>

      {showRank && (
        <ul
          className="absolute top-12 right-0 bg-bg border-2 border-sub-300 py-4 rounded-md shadow-slate-200 whitespace-nowrap flex flex-col animate-fade-down"
          ref={modalRef}
        >
          <div className="flex items-center mb-4 mx-4 space-x-4">
            <span className="text-xl font-bold inline-block">인기 검색어</span>
            <button type="button" onClick={() => setShowRank(false)}>
              <ChevronUpIcon className="w-5 h-5" />
            </button>
          </div>
          {rankData.data.map((v, i) => (
            <Link
              href={`/search/${decodeURI(v)}`}
              key={v}
              className="space-x-2 py-1.5 pl-4 pr-20 transition-colors hover:bg-[#181818]"
              onClick={() => setShowRank(false)}
            >
              <span>{i + 1}.</span>
              <span>{v}</span>
            </Link>
          ))}
        </ul>
      )}
    </section>
  );
  // return rankModal ? <RankModal rankBind={rankBind} /> : <RankClose rankBind={rankBind} />;
};

export default RankMenu;
