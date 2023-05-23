"use client";
import { useState, useEffect } from "react";
import { useFetchCategories, useFetchJobCategories } from "@/hooks/query";
import SortPosts from "@/components/BoardMain/SortPosts";
import Pagination from "@/components/Pagination";
import JobContentItem from "./JobContentItem";

import RightSideButton from "@/components/RightSideButton";
import { useCategoriesStore, usePageStore, useSortStore } from "@/store";
import { useFetchJobBoardList } from "@/hooks/query/useFetchJobBoardList";
import JobCategories from "./JobCategories";
import { useMemberStore } from "@/store/useMemberStore";
import NoDataExists from "@/components/Svg/NoDataExists";

/** 2023/05/18 - 자유게시판 메인 화면 - by leekoby */
const JobMain = () => {
  const member = useMemberStore((state) => state.member);
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

  return (
    <>
      <div className="mx-auto mt-6">
        <h1 className="text-2xl font-bold text-left">🔥 구인/구직 게시판 🔥</h1>
        {/* freeboard list header */}
        <div className="flex justify-end mb-4">
          <SortPosts />
        </div>
        <div className="flex flex-col md:flex-row ">
          {/* Left Side */}
          <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
            {/* category  */}
            {jobCategories && <JobCategories selectedCategory={selectedCategory} jobCategories={jobCategories} />}
          </aside>
          {/* rightside freeboard post list */}
          <section className="flex flex-col md:w-0 ml-5  grow-[8]">
            {/* post item */}
            <div className="space-y-5">
              {data?.pages[0].data.length === 0 ? (
                <NoDataExists />
              ) : (
                data?.pages.map((page) =>
                  page.data.map((innerData) => <JobContentItem props={innerData} key={innerData.jobBoardId} />),
                )
              )}

              {/* postslist bottom */}
              {data && (
                <div className="flex justify-center items-center">
                  {/* TODO: React Query를 이용한 PreFetch 방식으로 변경하기 */}
                  <Pagination
                    page={data?.pages[0].pageInfo.page}
                    totalPages={data?.pages[0].pageInfo.totalPages}
                    onPageChange={setCurrentPage}
                  />
                </div>
              )}
            </div>
          </section>
          {member && (
            <div className="fixed right-0 bottom-0 transform -translate-y-1/2 ml-2">
              <RightSideButton destination={`/job/write`} />
            </div>
          )}
        </div>
      </div>
    </>
  );
};
export default JobMain;
