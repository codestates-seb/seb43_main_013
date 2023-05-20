import BannerSlide from "@/components/BoardMain/Slide/BannerSlide";
import FeedbackPostsSlide from "@/components/BoardMain/Slide/FeedbackPostsSlide";
import FreePostsSlide from "@/components/BoardMain/Slide/FreePostsSlide";
import React from "react";

/** 2023/05/11 - 홈화면 구성 - by leekoby */

const HomeMain = () => {
  return (
    /** 2023/05/16 - 홈화면 구성 - by leekoby */
    <section className="flex flex-col space-y-24">
      <div className="">
        <BannerSlide />
      </div>

      <div className="flex flex-col">
        <FeedbackPostsSlide />
      </div>
      <div className="flex flex-col">
        <FreePostsSlide />
      </div>
      <div className="flex flex-col">
        홍보게시판 자리
        <FeedbackPostsSlide />
      </div>
      <div className="flex flex-col">
        인기동영상 자리
        <FeedbackPostsSlide />
      </div>
    </section>
  );
};
export default HomeMain;
