"use client";

import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import RightSideButton from "@/components/RightSideButton";
import NoDataExists from "@/components/Svg/NoDataExists";

import { useFetchCategories } from "@/hooks/query";
import useFetchPromotionBoardList from "@/hooks/query/useFetctPromotionBoardList";
import { useCategoriesStore, useSortStore } from "@/store";
import { useMemberStore } from "@/store/useMemberStore";
import { usePromotionCategoriesStore } from "@/store/usePromotionCategoriesStore";
import { useRef, useEffect, useCallback, useState } from "react";
import PromotionContentItem from "./PromotionContentItem";
import LoadingPage from "../loading";
import BoardError from "../boarderror";

/** 2023/05/17 - 홍보 게시판 메인 화면 - by leekoby */
const PromotionMain = () => {
  const member = useMemberStore((state) => state.member);

  const [isClient, setIsClient] = useState(typeof window !== "undefined");

  /** 2023/05/17 - 사이드 카테고리 상태 - by leekoby */
  const selectedCategory = useCategoriesStore((state) => state.selectedCategory);
  const selected =
    !selectedCategory || selectedCategory?.categoryName === "전체" ? "" : `/categories/${selectedCategory?.categoryId}`;

  /** 2023/05/17 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);

  /** 2023/05/17 홍보 목록 get 요청 - by leekoby */
  const { data, fetchNextPage, hasNextPage, isFetching, refetch, error, isError } = useFetchPromotionBoardList({
    selected,
    sorted: sortSelectedOption?.optionName,
    page: 1,
    size: 10,
  });

  useEffect(() => {
    refetch();
  }, [selectedCategory, sortSelectedOption]);

  /** 2023/05/17 - 공통 사이드 카테고리  - by leekoby */
  const { categories, isLoading } = useFetchCategories({ type: "normal" });

  /** 2023/05/17 - 무한스크롤 불러오기를 위해 사용 - by leekoby */
  const observerRef = useRef<IntersectionObserver | null>(null);

  /** 2023/05/17 - 무한스크롤 불러오기를 위한 lodaer 함수 - by leekoby */
  const loader = useCallback(
    (node: HTMLDivElement | null) => {
      if (isFetching || !hasNextPage) return;
      if (observerRef.current) observerRef.current.disconnect();
      const currentObserver = (observerRef.current = new IntersectionObserver(([entry]) => {
        if (entry.isIntersecting) fetchNextPage();
      }));
      if (node) currentObserver.observe(node);
    },
    [fetchNextPage, hasNextPage, isFetching],
  );

  if (isLoading) return <LoadingPage />;
  if (isError) return <BoardError />;

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 ">
      {isClient && (
        <div className="flex justify-between items-center mb-4">
          <h1 className="pl-10 text-2xl font-bold text-left"> 홍보 게시판 </h1>
          <SortPosts />
        </div>
      )}

      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className="flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* side category  */}
          {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}

          {/* post item */}
          {/*  2023/05/17 - 무한스크롤 피드백 게시글 목록 - by leekoby  */}
          <div className="flex flex-col flex-wrap gap-1 md:flex-row md:justify-around">
            {data?.pages[0].data.length === 0 ? (
              <NoDataExists />
            ) : (
              data?.pages.map((page, pageIndex) =>
                page.data.map((innerData, itemIndex) => {
                  const isLastItem = pageIndex === data.pages.length - 1 && itemIndex === page.data.length - 1;
                  return (
                    <div key={innerData.promotionBoardId} className="w-full lg:w-[49%]">
                      <PromotionContentItem props={innerData} ref={isLastItem ? loader : undefined} position="board" />
                    </div>
                  );
                }),
              )
            )}
          </div>
        </section>

        {/* 오른쪽 사이드 영역 */}
        {isClient && member && <RightSideButton destination={`/promotion/write`} />}
      </div>
    </div>
  );
};

export default PromotionMain;
