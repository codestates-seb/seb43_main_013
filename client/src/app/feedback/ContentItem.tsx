import Image from "next/image";
import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import defaultThumnail from "@/public/images/default-thumnail.jpg";
import sample_thumnail1 from "@/public/images/sample_thumnail1.png";
import ContentFooter from "../free/ContentFooter";
import TagItem from "../free/TagItem";

interface ContentItemProps {
  freeboardID?: number;
  title: string;
  link?: string;
  content: string;
  commentCount: number;
  viewCount: number;
  likeCount: number;
  tag: string[];
  category: string;
  createdAt: Date;
  nickname: string;
}
/** 2023/05/09 - 피드백 게시판 게시글 - by leekoby */
const ContentItem: React.FC<ContentItemProps> = (props) => {
  return (
    <>
      {/*  list container */}
      <div className="flex flex-col items-center m-2 bg-white rounded-md shadow-md md:flex-row md:w-full lg:w-[48%]">
        {/* List Item * */}
        <div className="flex flex-col items-center w-full h-full p-5 bg-gray-100 rounded-md ">
          {/* Thumnail */}
          <Image src={sample_thumnail1} className="w-auto h-auto mx-1 my-1 border rounded-lg " alt="" />
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
            <div className="flex gap-x-2">{props.tag && props.tag.map((item) => <TagItem tag={item} />)}</div>
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
    </>
  );
};

export default ContentItem;
