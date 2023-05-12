"use client";

import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import { useFetchFeedbackBoardList } from "@/hooks/query/useFetchFeedbackBoardList";
import Link from "next/link";
import ContentItem from "../feedback/ContentItem";
import FeedbackCategories from "./FeedbackCategories";

/** 2023/05/09 - 피드백 게시판 화면테스트용 더미데이터 - by leekoby */
const categoryDummyData = ["전체", "먹방", "게임", "스포츠", "이슈", "음악", "뷰티", "영화", "쿠킹", "동물", "IT"];

/** 2023/05/08 - 피드백 메인 화면 - by leekoby */
const FeedbackMain = () => {
  const { data, fetchNextPage, hasNextPage, isFetching } = useFetchFeedbackBoardList({ page: 1, size: 10 });

  if (!data) return;

  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">🔥 피드백 게시판 🔥</h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* category  */}
          <SideCategories categoryData={categoryDummyData} />
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-between my-1">
            <div className="flex text-3xl font-bold text-left ">
              [영상, 썸네일, ...]
              {/* <FeedbackCategories /> */}
            </div>

            <div className="">
              <SortPosts />
            </div>
          </div>

          {/* post item */}
          <div className="flex flex-col flex-wrap md:flex-row">
            {data.pages &&
              data.pages.map((item) =>
                item.data.map((innerData) => (
                  <Link href={`/feedback/${innerData.feedbackBoardId}`} className="lg:w-[48%]">
                    <ContentItem props={innerData} />
                  </Link>
                )),
              )}
          </div>

          <div className="flex flex-col items-center m-auto">무한스크롤</div>
        </section>
        {/* 🔺🔻 버튼? */}
      </div>
    </div>
  );
};

export default FeedbackMain;
