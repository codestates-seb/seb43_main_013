"use client";
import React, { useState, useEffect } from "react";
import { useSearchParams } from "next/navigation";
import { MagnifyingGlassIcon } from "../HeaderIcon";
import InputModal from "./InputModal";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = () => {
  const keyword = useSearchParams().get("keyword");
  const [inputModal, setInputModal] = useState(false);

  /** 2023/05/18 - 모달 열려있다면 스크롤 금지 - by 1-blue */
  useEffect(() => {
    // 모달이 열려있다면
    if (inputModal) {
      document.body.style.overflow = "hidden";
    }
    // 모달이 닫혀있다면
    else {
      document.body.style.overflow = "auto";
    }
  }, [inputModal]);

  return (
    <button
      className="group flex items-center border-2 md:border-[3px] w-full rounded-full mx-8 md:mx-16 border-sub-500 p-1.5 md:p-2 md:py-1 transition-colors hover:border-main-400"
      onClick={() => setInputModal(true)}
    >
      <MagnifyingGlassIcon className="w-5 h-5 md:w-7 md:h-7 text-sub-500 stroke-2 transition-colors group-hover:text-main-400" />
      {keyword && (
        <span className="ml-3 text-sub-700 hidden md:inline-block text-sm transition-colors group-hover:text-main-400">
          {keyword}
        </span>
      )}
      {inputModal && <InputModal setInputModal={setInputModal} />}
    </button>
  );
};

export default SearchDiv;
