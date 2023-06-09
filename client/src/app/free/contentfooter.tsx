"use client";
import {
  EyeIcon as ViewIcon,
  HandThumbUpIcon as LikeIcon,
  ChatBubbleLeftRightIcon as CommentsIcon,
  UserCircleIcon as DefaultAvatarIcon,
} from "@heroicons/react/24/outline";

interface ContentFooterProps {
  position: "side" | "main";
  nickName: string;
  createdAt?: Date;
  viewCount: number;
  likeCount: number;
  commentCount: number;
  textFontSize?: string;
  iconFontSize?: number;
}
/** 2023/05/08 - 게시글 하단 footer - by leekoby */
const ContentFooter: React.FC<ContentFooterProps> = ({
  position,
  nickName,
  createdAt,
  viewCount,
  likeCount,
  commentCount,
}) => {
  return (
    <div className="flex justify-between w-full px-3 py-1">
      <div className={` gap-x-1 flex items-center justify-start w-full`}>
        <DefaultAvatarIcon className={`${position === "side" ? " w-5 h-5 text-gray-500" : "w-7 h-7"} `} />

        <div className="flex flex-col items-start justify-center w-full">
          <div className={`${position === "side" ? "text-xs text-gray-500" : "text-sm font-bold"} cursor-pointer `}>
            {nickName}
          </div>
          {createdAt && <div className={`text-xs  text-gray-500`}>{createdAt.toLocaleDateString()}</div>}
        </div>
      </div>
      <div className={`flex gap-x-1 ${position === "main" && "self-end"}`}>
        <div className={`flex items-center justify-center text-gray-600 gap-x-1`}>
          <ViewIcon
            className={` ${position === "side" ? "text-gray-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
          />
          <div className={`${position === "side" ? "text-[10px] text-gray-500" : "text-xs text-black"} `}>
            {viewCount}
          </div>
        </div>
        <div className="flex items-center justify-center text-gray-600 gap-x-1">
          <LikeIcon
            className={` ${position === "side" ? "text-gray-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
          />
          <div className={`${position === "side" ? "text-[10px] text-gray-500" : "text-xs text-black"} `}>
            {likeCount}
          </div>
        </div>
        <div className="flex items-center justify-center text-gray-600 gap-x-1">
          <CommentsIcon
            className={` ${position === "side" ? "text-gray-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
          />
          <div className={`${position === "side" ? "text-[10px] text-gray-500" : "text-xs text-black"} `}>
            {commentCount}
          </div>
        </div>
      </div>
    </div>
  );
};
export default ContentFooter;
