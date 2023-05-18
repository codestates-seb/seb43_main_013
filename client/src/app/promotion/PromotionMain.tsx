"use client";

import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import RightSideButton from "@/components/RightSideButton";
import FullSpinner from "@/components/Spinner/FullSpinner";
import { useFetchCategories, useFetchPromotionCategories } from "@/hooks/query";
import useFetchPromotionBoardList from "@/hooks/query/useFetctPromotionBoardList";
import { useCategoriesStore, useSortStore } from "@/store";
import { usePromotionCategoriesStore } from "@/store/usePromotionCategoriesStore";
import Link from "next/link";
import { useRef, useEffect, useCallback } from "react";
import PromotionContentItem from "./PromotionContentItem";

/** 2023/05/17 - 홍보 게시판 메인 화면 - by leekoby */
const PromotionMain = () => {
  /** 2023/05/17 - 사이드 카테고리 상태 - by leekoby */
  const selectedCategory = useCategoriesStore((state) => state.selectedCategory);
  const selected =
    !selectedCategory || selectedCategory?.categoryName === "전체" ? "" : `/categories/${selectedCategory?.categoryId}`;

  /**  2023/05/17 - 홍보 카테고리 상태 - by leekoby */
  const selectedPromotionCategory = usePromotionCategoriesStore((state) => state.selectedPromotionCategory);

  /** 2023/05/17 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);

  /** 2023/05/17 홍보 목록 get 요청 - by leekoby */
  const { data, fetchNextPage, hasNextPage, isFetching, refetch, error, isError } = useFetchPromotionBoardList({
    selected,
    selectedPromotion: selectedPromotionCategory?.promotionCategoryId,
    sorted: sortSelectedOption?.optionName,
    page: 1,
    size: 10,
  });

  if (isError) {
    console.log(error);
  }
  // useEffect(() => {
  //   refetch();
  // }, [selectedCategory, sortSelectedOption, selectedPromotionCategory]);

  /** 2023/05/17 - 공통 사이드 카테고리  - by leekoby */
  const { categories, isLoading } = useFetchCategories({ type: "normal" });
  /** 2023/05/17 - 홍보 게시판 카테고리  - by leekoby */
  // const { promotionCategories, promotionIsLoading } = useFetchPromotionCategories({ type: "promotion" });

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

  if (!data) return <FullSpinner />;
  if (data.pages.length < 1) return <FullSpinner />;

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">🔥 홍보 게시판 🔥</h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className="flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* side category  */}
          {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}
          {/* <SideCategories categoryData={categoryDummyData} /> */}
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex flex-col md:flex-row md:justify-between ">
            {/* {promotionCategories && <PromotionCategories promotionCategoryData={promotionCategories} />} */}
            <div className="flex self-end">
              <SortPosts />
            </div>
          </div>
          {/* post item */}
          {/*  2023/05/17 - 무한스크롤 피드백 게시글 목록 - by leekoby  */}
          <div className="flex flex-col flex-wrap gap-5 md:flex-row">
            {/* TODO: //*게시글 북마크 좋아요 클릭되게 하는 방법 생각해보기  */}
            {data.pages.map((page, pageIndex) =>
              page.data.map((innerData, itemIndex) => {
                const isLastItem = pageIndex === data.pages.length - 1 && itemIndex === page.data.length - 1;
                return (
                  <Link
                    key={innerData.promotionBoardId}
                    href={`/feedback/${innerData.promotionBoardId}`}
                    className="lg:w-[48%]"
                  >
                    <PromotionContentItem props={innerData} ref={isLastItem ? loader : undefined} />
                  </Link>
                );
              }),
            )}
          </div>
          <div className="flex flex-col items-center m-auto">{}</div>
        </section>
        {/* 오른쪽 사이드 영역 */}
        <div className="flex flex-col items-center justify-center ml-2">
          <RightSideButton destination={`/feedback/write`} />
        </div>
      </div>
    </div>
  );
};

export default PromotionMain;
