import { useSortStore } from "@/store";
import { CheckIcon } from "@heroicons/react/20/solid";
import { useEffect, useState } from "react";

const sortOptions = [
  { optionId: 1, optionName: "최신순" },
  { optionId: 2, optionName: "인기순" },
  { optionId: 3, optionName: "등록순" },
];

/** 2023/05/10 - 최신순/인기순 정렬 컴포넌트 - by leekoby */
const SortPosts: React.FC = () => {
  const selectedOption = useSortStore((state) => state.selectedOption);
  const setSelectedOption = useSortStore((state) => state.setSelectedOption);

  const sortClickHandler = (option: { optionId: number; optionName: string }) => {
    setSelectedOption(option.optionName, option.optionId);
  };

  return (
    <>
      {!selectedOption ? (
        <></>
      ) : (
        <div className="flex gap-x-3" key={selectedOption.optionId}>
          {sortOptions.map((option) => (
            <li className="list-none" key={option.optionId}>
              <button
                type="button"
                className={`w-full px-5 text-sm leading-10 duration-200 rounded hover:bg-main-400 active:bg-main-500 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50
            ${
              selectedOption?.optionId === option.optionId
                ? "bg-main-500 text-white shadow-lg shadow-sub-500/50"
                : "bg-sub-100 "
            }`}
                onClick={() => {
                  sortClickHandler(option);
                }}
              >
                {selectedOption?.optionId === option.optionId && (
                  <CheckIcon className="inline-block w-4 h-4 ml-1 bg-sub-100 rounded-full text-main-400 mr-2" />
                )}
                {option.optionName}
              </button>
            </li>
          ))}
        </div>
      )}
    </>
  );
};

export default SortPosts;
