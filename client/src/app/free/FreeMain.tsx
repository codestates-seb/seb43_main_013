"use client";
import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import { useEffect, useState } from "react";
import axios from "axios";
import ContentItem from "./ContentItem";
import PopularPosts from "./PopularPosts";
import Link from "next/link";
import { ApiFetchFreeBoardListResponse } from "@/types/api";
import { useFetchCategories } from "@/hooks/query";
import { useFetchFreeBoardList } from "@/hooks/query/useFetchFreeBoardList";

/** 2023/05/08 - 자유게시판 메인 화면 - by leekoby */
const FreeMain = () => {
  // 자유게시판 목록 get 요청
  const [fetchData, setFetchData] = useState<ApiFetchFreeBoardListResponse>();

  const { data, fetchNextPage, hasNextPage, isFetching } = useFetchFreeBoardList({ page: 1, size: 10 });
  console.log(data?.pages);
  // const { categories } = useFetchCategories({ type: "normal" });

  // if (!categories) return;
  if (!data) return;

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">
        {/* <h1 className="text-3xl font-bold text-left" onClick={() => fetchNextPage()}> */}
        🔥 자유게시판 🔥
      </h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* category  */}
          <SideCategories categoryData={["전체"]} />
          <PopularPosts />
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-between">
            <h1 className="py-1 text-3xl font-bold text-left"> 전체 </h1>
            <div className="self-end px-2 py-1 text-gray-600 bg-white border border-gray-600 rounded-lg cursor-pointer outline outline-2 outline-gray-600">
              <SortPosts />
            </div>
          </div>

          {/* post item */}
          {data &&
            data.data.map((item) => (
              <Link href={`/free/${item.freeBoardId}`}>
                <ContentItem props={item} />
              </Link>
            ))}

          <div className="flex flex-col items-center m-auto"></div>
        </section>
        {/* 🔺🔻 버튼? */}
      </div>
    </div>
  );
};

export default FreeMain;
