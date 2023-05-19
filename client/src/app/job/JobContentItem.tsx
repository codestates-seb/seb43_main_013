import Image from "next/image";
import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import ContentFooter from "../../components/BoardMain/ContentFooter";
import TagItem from "../../components/BoardMain/TagItem";
import { JobBoard } from "@/types/api";

interface JobContentItemProps {
  props: JobBoard;
}

/** 2023/05/18 - 구인구직 게시판 메인 화면 게시글 - by leekoby */
const JobContentItem: React.FC<JobContentItemProps> = ({ props }) => {
  return (
    <>
      {/*  list container */}
      <div className="flex flex-col items-center justify-center mt-2 bg-white rounded-md shadow-md md:flex-row ">
        {/* List Item * */}
        <div className="flex flex-col items-center w-full h-full p-5 bg-sub-100 rounded-md ">
          {/* Thumnail */}
          {/* <Image src={defaultThumnail} className="h-auto mx-1 my-1 border rounded-lg w-80 md:w-40" alt="" /> */}
          {/* right content */}
          <div className="flex justify-between w-full h-full">
            {/* content header */}
            <div className="flex flex-col justify-between w-full h-full gap-2 p-2 md:p-3">
              <div className="flex items-center justify-between ">
                {/* rightside title */}
                <h1 className="w-full text-xl font-bold text-left">{props.title}</h1>
                <BookmarkIconUnchecked className="w-5 h-5 text-black cursor-pointer " />
              </div>
              {/* content body */}
              <p className="flex-1 w-full h-full text-left break-all line-clamp-3">{props.content}</p>
              {/* rightside tag */}
              <div className="flex w-full gap-x-2">
                {props.tags && props.tags.map((item) => <TagItem tag={item.tagName} />)}
              </div>
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
    </>
  );
};
export default JobContentItem;