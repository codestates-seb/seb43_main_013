import Image from "next/image";
import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import defaultThumnail from "@/public/images/default-thumnail.jpg";
import ContentFooter from "./contentfooter";
import TagItem from "./TagItem";

interface ContentItemProps {
  freeboardID?: number;
  title: string;
  content: string;
  commentCount: number;
  viewCount: number;
  likeCount: number;
  tag: string[];
  category: string;
  createdAt: Date;
  nickname: string;
}

const ContentItem: React.FC<ContentItemProps> = (props) => {
  return (
    <>
      {/*  list container */}
      <div className="flex flex-col items-center justify-center mt-2 bg-white rounded-md shadow-md md:flex-row ">
        {/* List Item * */}
        <div className="flex flex-col items-center w-full mx-5 my-5 bg-gray-100 rounded-md md:flex-row">
          {/* Thumnail */}
          <Image src={defaultThumnail} className="h-auto my-12 ml-6 border rounded-lg w-80 md:w-32 md:h-28" alt="" />
          {/* right content */}
          <div className="flex flex-col w-full gap-2 p-2 md:p-5">
            {/* content header */}
            <div className="flex items-center justify-between">
              {/* rightside title */}
              <h1 className="text-xl font-bold text-left ">{props.title}</h1>
              <BookmarkIconUnchecked className="w-5 h-5 text-black cursor-pointer " />
            </div>

            {/* content body */}
            <p className="w-full text-left break-all line-clamp-3 ">{props.content}</p>
            {/* rightside tag */}
            <div className="flex gap-x-2">{props.tag && props.tag.map((item) => <TagItem tag={item} />)}</div>
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
};

export default ContentItem;
