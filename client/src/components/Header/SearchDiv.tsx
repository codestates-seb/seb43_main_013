"use client";
import React, { useState } from "react";
import { MagnifyingGlassIcon } from "../HeaderIcon";
import InputModal from "./InputModal";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = () => {
  const [inputModal, setInputModal] = useState(false);

  return (
    <button
      className="bg-main-300 w-full flex justify-end mr-5 ml-10 rounded-xl h-10 items-center px-5 py-1 cursor-pointer"
      onClick={() => {
        setInputModal(true);
      }}
    >
      {inputModal && <InputModal setInputModal={setInputModal} />}
      <MagnifyingGlassIcon className="w-6 text-white" />
    </button>
  );
};

export default SearchDiv;
