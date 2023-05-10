"use client";

import React, { ChangeEventHandler, useState } from "react";

const SortPosts = () => {
  const [select, setSelect] = useState("new");

  const handleSelect: ChangeEventHandler<HTMLSelectElement> = (e) => {
    setSelect(e.target.value);
    console.log(select);
  };

  return (
    <select
      className="block py-2.5 px-0 w-full text-sm text-black bg-transparent border-0 border-b-2 border-gray-200 appearance-none    focus:outline-none focus:ring-0 focus:border-gray-200 peer"
      name="state"
      id=""
      onChange={handleSelect}
    >
      <option value="new" className="">
        최신순
      </option>
      <option value="popular">인기순 </option>
    </select>
  );
};
export default SortPosts;
