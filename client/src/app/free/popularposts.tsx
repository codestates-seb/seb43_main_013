"use client";

import ContentFooter from "../../components/BoardMain/ContentFooter";
import TagItem from "../../components/BoardMain/TagItem";

/** 2023/05/09 - 임시 자유게시판 사이드 인기게시글 - by leekoby */
const PopularPosts = () => {
  return (
    <div className=" md:w-full">
      {/* left Container */}
      <div className="hidden xl:flex flex-col shadow-md my-2.5 rounded-xl ">
        {/* left header */}
        <h1 className="flex ml-5 text-xl font-bold mt-7 ">👀 인기 게시글 👀 </h1>

        {/* left Item Container */}
        <div className="pb-1 pr-1 m-5 bg-sub-100 rounded w-53 ">
          {/* side title */}
          <h2 className="mt-1.5 ml-2.5 mb-2 font-bold text-base text-left align-middle line-clamp-2">
            인기 게시글 내용 부분 인기 게시글 내용 부분 인기 게시글 내용 부분
          </h2>
          {/* side tags */}
          <div className="ml-2.5 flex text-sm gap-x-2"></div>
          {/* leftside footer */}
          {/* <ContentFooter position="side"  /> */}
        </div>
      </div>
    </div>
  );
};

export default PopularPosts;
