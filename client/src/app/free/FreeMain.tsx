"use client";
import Link from "next/link";
import { useState, useEffect } from "react";
import { useFetchFreeBoardList } from "@/hooks/query/useFetchFreeBoardList";
import { useFetchCategories } from "@/hooks/query";
import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import Pagination from "@/components/Pagination";
import ContentItem from "./ContentItem";
import FullSpinner from "@/components/Spinner/FullSpinner";
import RightSideButton from "@/components/RightSideButton";
import { useCategoriesStore, usePageStore, useSortStore } from "@/store";

/** 2023/05/08 - 자유게시판 메인 화면 - by leekoby */
// TODO: 렌더링 3번씩 되는 문제 있음
const FreeMain = () => {
  /** 2023/05/14 - 게시판 page 상태관리 - by leekoby */
  const currentPage = usePageStore((state) => state.currentPage);
  const setCurrentPage = usePageStore((state) => state.setCurrentPage);

  /** 2023/05/14 - 게시판 사이드 카테고리 상태관리 - by leekoby */
  const selectedCategory = useCategoriesStore((state) => state.selectedCategory);
  const selected =
    !selectedCategory || selectedCategory?.categoryName === "전체" ? "" : `/categories/${selectedCategory?.categoryId}`;

  /** 2023/05/15 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);

  /** 2023/05/11 자유게시판 목록 get 요청 - by leekoby */
  const { data, refetch } = useFetchFreeBoardList({
    selected,
    sorted: sortSelectedOption?.optionName,
    page: currentPage,
    size: 10,
  });

  useEffect(() => {
    refetch();
  }, [selectedCategory, sortSelectedOption, currentPage]);

  /** 2023/05/13 - 공통 카테고리 초기값 - by leekoby */
  const { categories, isLoading } = useFetchCategories({ type: "normal" });

  if (!data) return <FullSpinner />;
  if (data.pages.length < 1) return <FullSpinner />;

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">🔥 자유게시판 🔥</h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* category  */}
          {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}

          {/* TODO: //* 인기게시글 생기면 완성하기 */}
          {/* <PopularPosts /> */}
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-end">
            <SortPosts />
          </div>

          {/* post item */}
          {/* TODO: //*게시글 북마크 좋아요 클릭되게 하는 방법 생각해보기  */}
          {data.pages.map((item) =>
            item.data.map((innerData) => (
              <Link href={`/free/${innerData.freeBoardId}`}>
                <ContentItem props={innerData} key={innerData.freeBoardId} />
              </Link>
            )),
          )}

          {/* postslist bottom */}
          <div className="flex justify-center items-center">
            {/* TODO React Query를 이용한 PreFetch 방식으로 변경하기 */}
            <Pagination
              page={data?.pages[0].pageInfo.page}
              totalPages={data?.pages[0].pageInfo.totalPages}
              onPageChange={setCurrentPage}
            />
          </div>
        </section>
        {/* 오른쪽 사이드 영역 */}
        <div className="flex flex-col items-center justify-center ml-2">
          <RightSideButton destination={`/free/write`} />
        </div>
      </div>
    </div>
  );
};

export default FreeMain;
