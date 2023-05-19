import Image from "next/image";
import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import sample_thumnail2 from "@/public/images/sample_thumnail2.png";
import ContentFooter from "../../components/BoardMain/ContentFooter";
import TagItem from "../../components/BoardMain/TagItem";
import { forwardRef } from "react";
import { getYoutubeThumbnail } from "@/libs";
import { PromotionBoard } from "@/types/api";

interface ContentItemProps {
  props: PromotionBoard;
}

/** 2023/05/17 - 홍보 게시판 게시글 - by leekoby */

const PromotionContentItem = forwardRef<HTMLDivElement, ContentItemProps>(({ props }, ref) => {
  return (
    <>
      {/*  list container */}
      <div ref={ref} className="flex flex-col items-center m-2 bg-white rounded-md shadow-md md:flex-row md:w-full ">
        <div
          ref={ref}
          className="h-full flex flex-col items-center m-2 bg-white rounded-md shadow-md md:flex-row md:w-full "
        >
          {/* List Item * */}
          <div className="flex flex-col items-center w-full h-full p-5 bg-sub-100 rounded-md ">
            {/* Thumnail */}
            <Image src={sample_thumnail2} className="w-auto h-auto mx-1 my-1 border rounded-lg " alt="" />
            {/* right content */}

            <div className="flex flex-col w-full h-full gap-2 p-2">
              {/* content header */}
              <div className="flex items-center justify-between">
                {/* rightside title */}
                <h1 className="text-xl font-bold text-left ">{props.title}</h1>
                <BookmarkIconUnchecked className="w-5 h-5 text-black cursor-pointer " />
              </div>
              {/* content body */}
              <p className="flex-1 w-full text-left break-all line-clamp-3">{props.content}</p>
              {/* rightside tag */}
              <div className="flex gap-x-2">
                {props.tags && props.tags.map((item) => <TagItem tag={item.tagName} />)}
              </div>
            </div>
            <ContentFooter
              position="main"
              nickName={props.nickname}
              viewCount={props.viewCount}
              likeCount={props.likeCount}
              commentCount={props.commentCount}
              createdAt={props.createdAt}
            />
          </div>
        </div>
      </div>
    </>
  );
});

export default PromotionContentItem;
