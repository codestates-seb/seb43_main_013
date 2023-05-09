"use client";
import { useState } from "react";

/** 2023/05/04 - 네브 컴포넌트 - by Kadesti */
const Nav = () => {
  const [rankModal, setRankModal] = useState(false);
  const currentRank = [
    {
      id: 1,
      content: "해방일지",
    },
    {
      id: 2,
      content: "2",
    },
    {
      id: 3,
      content: "3",
    },
    {
      id: 4,
      content: "4",
    },
    {
      id: 5,
      content: "5",
    },
    {
      id: 6,
      content: "6",
    },
    {
      id: 7,
      content: "7",
    },
    {
      id: 8,
      content: "8",
    },
    {
      id: 9,
      content: "9",
    },
    {
      id: 10,
      content: "10",
    },
  ];

  return (
    <nav className="bg-white relative h-[64px] shadow-xl flex justify-center items-center px-7">
      <div className="max-w-[1080px] w-full flex justify-between items-center">
        <div className="max-w-[300px] w-80 flex justify-between text-xl ">
          <div className="cursor-pointer text-black hover:text-rose-400">자유</div>
          <div className="cursor-pointer text-black hover:text-rose-400">피드백</div>
          <div className="cursor-pointer text-black hover:text-rose-400">홍보</div>
          <div className="cursor-pointer text-black hover:text-rose-400">구인</div>
        </div>
        <div className="flex w-20 justify-between mr-6 cursor-default">
          <div>1</div>
          <div
            onClick={() => {
              setRankModal(!rankModal);
            }}
            className="text-black hover:text-rose-400 cursor-pointer"
          >
            해방일지
          </div>
        </div>

        {rankModal && (
          <div className="flex flex-col p-2 w-28 absolute right-0 top-16 justify-between mr-6 cursor-default bg-white shadow-xl rounded-b-lg">
            {currentRank.map((el) => {
              return (
                <div className="flex justify-between p-2">
                  <div>{el.id}</div>
                  <div className="text-black hover:text-rose-400 cursor-pointer">{el.content}</div>
                </div>
              );
            })}
          </div>
        )}
      </div>
    </nav>
  );
};

export default Nav;
