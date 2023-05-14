"use client";
import { useFetchFreeBoardList } from "@/hooks/query/useFetchFreeBoardList";
import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import ContentItem from "./ContentItem";
// TODO 사이드 인기 게시글 영역 API 생성시 추가하기
// import PopularPosts from "./PopularPosts";
import Link from "next/link";
import { useFetchCategories } from "@/hooks/query";
import Pagination from "@/components/Pagination";
import { useState } from "react";
import { ResponseCategoriesType } from "@/types/api";
import usePageStore from "@/store/pageStore";

/** 2023/05/08 - 자유게시판 메인 화면 - by leekoby */
const FreeMain = () => {
  // TODO page 정보 전역상태 추가하기

  const currentPage = usePageStore((state) => state.currentPage);
  const setCurrentPage = usePageStore((state) => state.setCurrentPage);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [sorted, setSorted] = useState("최신순");
  /** 2023/05/11 자유게시판 목록 get 요청 - by leekoby */
  const { data, fetchNextPage, hasNextPage, isFetching, refetch } = useFetchFreeBoardList({
    sorted,
    page: currentPage,
    size: 4,
  });
  /** 2023/05/13 - 자유게시판 카테고리 초기값 - by leekoby */
  const { categories, isLoading } = useFetchCategories({ type: "normal" });

  if (!categories) return <></>;
  if (!data) return <></>;
  if (data.pages.length < 1) return <></>;

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
          {categories && <SideCategories categoryData={categories} setSelectedCategory={setSelectedCategory} />}

          {/* // TODO 인기게시글 생기면 완성하기 */}
          {/* <PopularPosts /> */}
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-between">
            {/* 선택된 카테고리 보여주기 */}
            <h1 className="py-1 text-3xl font-bold text-left">{selectedCategory}</h1>

            <div className="self-end px-2 py-1 text-sub-600 bg-white border border-sub-600 rounded-lg cursor-pointer outline outline-2 outline-sub-600">
              <SortPosts />
            </div>
          </div>

          {/* post item */}
          {data.pages &&
            data.pages.map((item) =>
              item.data.map((innerData) => (
                <Link key={innerData.freeBoardId} href={`/free/${innerData.freeBoardId}`}>
                  <ContentItem props={innerData} />
                </Link>
              )),
            )}

          {/* postslist bottom */}
          <div className="flex justify-between items-center">
            {/* TODO React Query를 이용한 PreFetch 방식으로 변경하기 */}
            <Pagination
              page={data?.pages[0].pageInfo.page}
              totalPages={data?.pages[0].pageInfo.totalPages}
              onPageChange={setCurrentPage}
            />
            <Link
              href={`/free/write`}
              className="flex justify-center items-center bg-main-400 text-white mt-8 mb-4 px-3 py-2 rounded-sm text-sm font-bold transition-colors hover:bg-main-500 active:bg-main-600  focus:outline-none focus:bg-main-500 focus:ring-2 focus:ring-main-500 focus:ring-offset-2"
            >
              글쓰기
            </Link>
          </div>
        </section>
        {/* 🔺🔻 버튼? */}
      </div>
    </div>
  );
};

export default FreeMain;
