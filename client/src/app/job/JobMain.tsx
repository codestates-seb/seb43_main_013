"use client";
import Link from "next/link";
import { useState, useEffect } from "react";
import { useFetchCategories, useFetchJobCategories } from "@/hooks/query";
import SortPosts from "@/components/BoardMain/SortPosts";
import Pagination from "@/components/Pagination";
import JobContentItem from "./JobContentItem";
import FullSpinner from "@/components/Spinner/FullSpinner";
import RightSideButton from "@/components/RightSideButton";
import { useCategoriesStore, usePageStore, useSortStore } from "@/store";
import { useFetchJobBoardList } from "@/hooks/query/useFetchJobBoardList";
import JobCategories from "./JobCategories";
/** 2023/05/18 - 자유게시판 메인 화면 - by leekoby */
const JobMain = () => {
  /** 2023/05/18 - 게시판 page 상태관리 - by leekoby */
  const currentPage = usePageStore((state) => state.currentPage);
  const setCurrentPage = usePageStore((state) => state.setCurrentPage);

  /** 2023/05/18 - 게시판 사이드 카테고리 상태관리 - by leekoby */
  const selectedCategory = useCategoriesStore((state) => state.selectedCategory);
  const selected =
    !selectedCategory || selectedCategory?.categoryName === "전체"
      ? ""
      : `/jobcategories/${selectedCategory?.categoryId}`;

  /** 2023/05/18 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);

  /** 2023/05/18 구인구직 게시판 목록 get 요청 - by leekoby */
  const { data, refetch } = useFetchJobBoardList({
    selected,
    sorted: sortSelectedOption?.optionName,
    page: currentPage,
    size: 10,
  });

  useEffect(() => {
    refetch();
  }, [selectedCategory, sortSelectedOption, currentPage]);

  /** 2023/05/18 - 구인구직 카테고리 초기값 요청 - by leekoby */
  const { jobCategories, jobCategoryIsLoading } = useFetchJobCategories({ type: "job" });
  console.log(jobCategories, selectedCategory, selected);

  if (!data) return <FullSpinner />;
  if (data.pages.length < 1) return <FullSpinner />;
  return (
    <>
      <div className="mx-auto mt-6 min-w-min">
        <h1 className="text-3xl font-bold text-left">🔥 구인/구직 게시판 🔥</h1>
        <div className="flex flex-col md:flex-row ">
          {/* Left Side */}
          <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
            {/* category  */}
            {/* TODO: 구인구직게시판 카테고리로 변경하기 */}
            {jobCategories && <JobCategories selectedCategory={selectedCategory} jobCategories={jobCategories} />}
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
                <Link key={innerData.jobBoardId} href={`/job/${innerData.jobBoardId}`}>
                  <JobContentItem props={innerData} />
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
          <div className="flex flex-col items-center justify-center ml-2">
            <RightSideButton destination={`/job/write`} />
          </div>
        </div>
      </div>
    </>
  );
};
export default JobMain;
