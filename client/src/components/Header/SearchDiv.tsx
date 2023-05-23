"use client";
import React, { useState, useEffect } from "react";
import { MagnifyingGlassIcon } from "../HeaderIcon";
import InputModal from "./InputModal";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = () => {
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
      className="group ml-auto border-2 w-auto md:w-48 rounded-full mr-8 md:mr-16 border-sub-500 md:rounded-sm px-2 py-2 md:py-1 transition-colors hover:border-main-400"
      onClick={() => setInputModal(true)}
    >
      <MagnifyingGlassIcon className="w-6 h-6 text-sub-500 stroke-2 transition-colors group-hover:text-main-400" />
      {inputModal && <InputModal setInputModal={setInputModal} />}
    </button>
  );
};

export default SearchDiv;
