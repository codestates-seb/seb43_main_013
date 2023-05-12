import MainSlide from "@/components/BoardMain/Slide/BannerSlide";
import PostsSlide from "@/components/BoardMain/Slide/PostsSlide";
import React from "react";

/** 2023/05/11 - 홈화면 구성 - by leekoby */
const HomeMain = () => {
  return (
    <section className="flex flex-col">
      <div className="">
        <MainSlide />
      </div>
      <div className="">
        <PostsSlide />
      </div>
    </section>
  );
};
export default HomeMain;
