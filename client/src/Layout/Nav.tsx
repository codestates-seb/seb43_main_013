"use client";
import Link from "next/link";
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
          <Link href="/free">
            <div className="text-black cursor-pointer hover:text-rose-400">자유</div>
          </Link>
          <Link href="feedback">
            <div className="text-black cursor-pointer hover:text-rose-400">피드백</div>
          </Link>
          <Link href="/promotion">
            <div className="text-black cursor-pointer hover:text-rose-400">홍보</div>
          </Link>
          <Link href="/job">
            <div className="text-black cursor-pointer hover:text-rose-400">구인</div>
          </Link>
        </div>
        <div className="flex justify-between w-20 mr-6 cursor-default">
          <div>1</div>
          <div
            onClick={() => {
              setRankModal(!rankModal);
            }}
            className="text-black cursor-pointer hover:text-rose-400"
          >
            해방일지
          </div>
        </div>

        {rankModal && (
          <div className="absolute right-0 flex flex-col justify-between p-2 mr-6 bg-white rounded-b-lg shadow-xl cursor-default w-28 top-16">
            {currentRank.map((el) => {
              return (
                <div className="flex justify-between p-2">
                  <div>{el.id}</div>
                  <div className="text-black cursor-pointer hover:text-rose-400">{el.content}</div>
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
