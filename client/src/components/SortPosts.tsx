"use client";
import { Listbox } from "@headlessui/react";
import React, { useState, Fragment } from "react";
import { CheckIcon } from "@heroicons/react/20/solid";

const sortOptions = [
  { id: 1, name: "최신순", unavailable: false },
  { id: 2, name: "인기순", unavailable: false },
];

/** 2023/05/10 - 최신순/인기순 정렬 컴포넌트 - by leekoby */

const SortPosts = () => {
  const [selected, setSelected] = useState(sortOptions[0]);

  return (
    <Listbox value={selected} onChange={setSelected}>
      <Listbox.Button>{selected.name}</Listbox.Button>
      <Listbox.Options>
        {sortOptions.map((item) => (
          <Listbox.Option key={item.id} value={item} disabled={item.unavailable}>
            {item.name}
          </Listbox.Option>
        ))}
      </Listbox.Options>
    </Listbox>
  );
  //   const [select, setSelect] = useState("");
  //   const handleSelect: ChangeEventHandler<HTMLSelectElement> = (e) => {
  //     setSelect(e.target.value);
  //   };
  //   useEffect(() => {
  //     console.log(select);
  //   }, [select]);
  //   return (
  //     <select
  //       className="block py-2.5 px-0 w-full text-sm text-black bg-transparent border-0 border-b-2 border-gray-200 appearance-none    focus:outline-none focus:ring-0 focus:border-gray-200 peer"
  //       name="state"
  //       id=""
  //       onChange={handleSelect}
  //     >
  //       <option value="new" className="">
  //         최신순
  //       </option>
  //       <option value="popular">인기순</option>
  //     </select>
  //   );
};
export default SortPosts;
