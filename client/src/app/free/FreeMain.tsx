"use client";
import { useEffect, useState } from "react";
import { useFetchFreeBoardList } from "@/hooks/query/useFetchFreeBoardList";
import { useFetchCategories } from "@/hooks/query";
import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import Pagination from "@/components/Pagination";
import ContentItem from "./ContentItem";
import RightSideButton from "@/components/RightSideButton";
import { useCategoriesStore, usePageStore, useSortStore } from "@/store";
import { useMemberStore } from "@/store/useMemberStore";
import NoDataExists from "@/components/Svg/NoDataExists";
import LoadingPage from "../loading";
import BoardError from "../boarderror";

/** 2023/05/08 - 자유게시판 메인 화면 - by leekoby */
const FreeMain = () => {
  const member = useMemberStore((state) => state.member);

  const [isClient, setIsClient] = useState(false);

  useEffect(() => {
    setIsClient(true);
  }, []);

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
  const { data, refetch, isError, isLoading, error } = useFetchFreeBoardList({
    selected,
    sorted: sortSelectedOption?.optionName,
    page: currentPage,
    size: 10,
  });

  useEffect(() => {
    refetch();
  }, [selectedCategory, sortSelectedOption, currentPage]);

  /** 2023/05/13 - 공통 카테고리 초기값 - by leekoby */
  const { categories } = useFetchCategories({ type: "normal" });

  if (isLoading) return <LoadingPage />;
  if (isError) return <BoardError />;

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6">
      {isClient && (
        <div className="flex justify-between items-center mb-4">
          <h2 className="pl-10 text-2xl font-bold text-left"> 자유 게시판 </h2>
          <SortPosts />
        </div>
      )}
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        {/* rightside freeboard post list */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* category  */}
          {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}
        </aside>
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}

          {/* post item */}
          <div className="space-y-5">
            {data?.pages[0].data.length === 0 ? (
              <NoDataExists />
            ) : (
              data?.pages.map((page) =>
                page.data.map((innerData) => <ContentItem props={innerData} key={innerData.freeBoardId} />),
              )
            )}

            {/* postslist bottom */}

            {data && (
              <div className="flex justify-center items-center">
                <Pagination
                  page={data?.pages[0].pageInfo.page}
                  totalPages={data?.pages[0].pageInfo.totalPages}
                  onPageChange={setCurrentPage}
                />
              </div>
            )}
          </div>
        </section>
        {/* 오른쪽 사이드 영역 */}
        {isClient && member && <RightSideButton destination={`/free/write`} />}
      </div>
    </div>
  );
};

export default FreeMain;
