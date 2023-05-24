"use client";

import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import RightSideButton from "@/components/RightSideButton";
import { useFetchCategories, useFetchFeedbackCategories } from "@/hooks/query";
import { useFetchFeedbackBoardList } from "@/hooks/query/useFetchFeedbackBoardList";
import { useCategoriesStore, useSortStore } from "@/store";
import { useFeedbackCategoriesStore } from "@/store/useFeedbackCategoriesStore";
import { useRef, useEffect, useCallback, useState } from "react";
import FeedbackContentItem from "./FeedbackContentItem";
import FeedbackCategories from "./FeedbackCategories";
import { useMemberStore } from "@/store/useMemberStore";
import NoDataExists from "@/components/Svg/NoDataExists";

/** 2023/05/08 - 피드백 게시판 메인 화면 - by leekoby */
const FeedbackMain = () => {
  const member = useMemberStore((state) => state.member);

  const [isClient, setIsClient] = useState(false);

  useEffect(() => {
    setIsClient(true);
  }, []);
  /** 2023/05/14 - 사이드 카테고리 상태 - by leekoby */

  const selectedCategory = useCategoriesStore((state) => state.selectedCategory);

  /**  2023/05/15 - 피드백 카테고리 상태 - by leekoby */
  const selectedFeedbackCategory = useFeedbackCategoriesStore((state) => state.selectedFeedbackCategory);

  /** 2023/05/15 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);

  const selected = selectedCategory?.categoryName === "전체" ? "" : `/categories/${selectedCategory?.categoryId}`;

  const selectFeedback =
    selectedFeedbackCategory?.feedbackCategoryName === "전체"
      ? ""
      : `/feedbackcategories/${selectedFeedbackCategory?.feedbackCategoryId}`;

  /** 2023/05/11 피드백 목록 get 요청 - by leekoby */
  const { data, fetchNextPage, hasNextPage, isFetching, refetch } = useFetchFeedbackBoardList({
    selected,
    selectedFeedback: selectFeedback,
    sorted: sortSelectedOption?.optionName,
    page: 1,
    size: 10,
  });

  useEffect(() => {
    refetch();
  }, [selectedCategory, sortSelectedOption, selectedFeedbackCategory]);

  /** 2023/05/13 - 공통 사이드 카테고리  - by leekoby */
  const { categories, isLoading } = useFetchCategories({ type: "normal" });
  /** 2023/05/13 - 피드백게시판 피드백 카테고리  - by leekoby */
  const { feedbackCategories, feedbackCategoryIsLoading } = useFetchFeedbackCategories({ type: "feedback" });

  /** 2023/05/14 - 무한스크롤 불러오기를 위해 사용 - by leekoby */
  const observerRef = useRef<IntersectionObserver | null>(null);
  /** 2023/05/14 - 무한스크롤 불러오기를 위한 lodaer 함수 - by leekoby */
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

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6">
      <h1 className="text-2xl font-bold text-left"> 피드백 게시판 </h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className="flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2] md:mt-14 ">
          {/* side category  */}
          {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {isClient && (
            <div className="flex flex-col md:flex-row md:justify-between mb-4 ">
              {feedbackCategories && <FeedbackCategories feedbackCategoryData={feedbackCategories} />}
              <div className="flex justify-end  mb-4">
                <SortPosts />
              </div>
            </div>
          )}
          {/* freeboard list header */}

          {/* post item */}
          {/*  2023/05/14 - 무한스크롤 피드백 게시글 목록 - by leekoby  */}
          <div className="flex flex-col flex-wrap gap-5 md:flex-row">
            {data?.pages[0].data.length === 0 ? (
              <NoDataExists />
            ) : (
              data?.pages.map((page, pageIndex) =>
                page.data.map((innerData, itemIndex) => {
                  const isLastItem = pageIndex === data.pages.length - 1 && itemIndex === page.data.length - 1;
                  return (
                    <div key={innerData.feedbackBoardId} className="w-full lg:w-[48%]">
                      <FeedbackContentItem props={innerData} ref={isLastItem ? loader : undefined} position="board" />
                    </div>
                  );
                }),
              )
            )}
          </div>
          {isClient && member && <RightSideButton destination={`/feedback/write`} />}
        </section>
        {/* 오른쪽 사이드 영역 */}
      </div>
    </div>
  );
};

export default FeedbackMain;
