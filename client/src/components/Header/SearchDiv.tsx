import React, { useState } from "react";
import { MagnifyingGlassIcon } from "../HeaderIcon";
import InputModal from "./InputModal";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = () => {
  const [inputModal, setInputModal] = useState(false);

  return (
    <div
      className="bg-main-300 w-full flex justify-end mx-10 rounded-xlr h-10 items-center px-5 py-1 cursor-pointer"
      onClick={() => {
        setInputModal(true);
      }}
    >
      <div className="w-full h-full border-b-2 border-white mr-5" />
      {inputModal && <InputModal setInputModal={setInputModal} />}
      <MagnifyingGlassIcon className="w-6 text-white" />
    </div>
  );
};

export default SearchDiv;
