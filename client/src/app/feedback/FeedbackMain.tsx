"use client";

import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import { useFetchCategories, useFetchFeedbackCategories } from "@/hooks/query";
import { useFetchFeedbackBoardList } from "@/hooks/query/useFetchFeedbackBoardList";
import { useQueryClient } from "@tanstack/react-query";
import Link from "next/link";
import { useState, useRef, useEffect, useCallback } from "react";
import ContentItem from "../feedback/ContentItem";
import FeedbackCategories from "./FeedbackCategories";

/** 2023/05/08 - 피드백 메인 화면 - by leekoby */
const FeedbackMain = () => {
  /** 2023/05/14 - 게시글 추가를 위해 사용 - by leekoby */

  /** 2023/05/14 - 정렬을 위해 사용 - by leekoby */
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedFeedbackCategory, setSelectedFeedbackCategory] = useState("");
  const [sorted, setSorted] = useState("최신순");

  const { data, fetchNextPage, hasNextPage, isFetching } = useFetchFeedbackBoardList({ sorted, page: 1, size: 10 });

  const { categories, isLoading } = useFetchCategories({ type: "feedback" });
  const { feedbackCategories, feedbackIsLoading } = useFetchFeedbackCategories({ type: "feedback" });

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

  console.log(data);
  if (!categories) return <></>;
  if (!data) return <></>;
  if (data.pages.length < 1) return <></>;
  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">🔥 피드백 게시판 🔥</h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* side category  */}
          {categories && <SideCategories categoryData={categories} setSelectedCategory={setSelectedCategory} />}
          {/* <SideCategories categoryData={categoryDummyData} /> */}
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-between my-1">
            {feedbackCategories && (
              <FeedbackCategories
                feedbackCategoryData={feedbackCategories}
                setSelectedFeedbackCategory={setSelectedFeedbackCategory}
              />
            )}
            <div className="">
              <SortPosts />
            </div>
          </div>
          {/* post item */}

          {/*  2023/05/14 - 무한스크롤 피드백 게시글 목록 - by leekoby  */}
          <div className="flex flex-col flex-wrap gap-5 md:flex-row">
            {data.pages &&
              data.pages.map((page, pageIndex) =>
                page.data.map((innerData, itemIndex) => {
                  const isLastItem = pageIndex === data.pages.length - 1 && itemIndex === page.data.length - 1;
                  return (
                    <Link
                      key={innerData.feedbackBoardId}
                      href={`/feedback/${innerData.feedbackBoardId}`}
                      className="lg:w-[48%]"
                    >
                      <ContentItem props={innerData} ref={isLastItem ? loader : undefined} />
                    </Link>
                  );
                }),
              )}
          </div>
          <div className="flex flex-col items-center m-auto">{}</div>
        </section>
        {/* 🔺🔻 버튼? */}
      </div>
    </div>
  );
};

export default FeedbackMain;
