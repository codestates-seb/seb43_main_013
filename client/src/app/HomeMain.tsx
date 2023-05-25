import BannerSlide from "@/app/BannerSlide";
import FeedbackPostsSlide from "@/app/FeedbackPostsSlide";
import FreePostsSlide from "@/app/FreePostsSlide";
import React from "react";
import YoutubeList from "./YoutubeSlide";

/** 2023/05/11 - 홈화면 구성 - by leekoby */

const HomeMain = () => {
  return (
    /** 2023/05/16 - 홈화면 구성 - by leekoby */
    <section className="flex flex-col space-y-24 mb-36">
      <div className="flex flex-col mt-10">
        <YoutubeList />
      </div>
      <div className="flex flex-col ">
        <FeedbackPostsSlide />
      </div>
      <div className="flex flex-col">
        <FreePostsSlide />
      </div>
    </section>
  );
};
export default HomeMain;
