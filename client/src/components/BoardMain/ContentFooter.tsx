"use client";
import {
  EyeIcon as ViewIcon,
  HandThumbUpIcon as FalseLikeIcon,
  ChatBubbleLeftRightIcon as CommentsIcon,
  UserCircleIcon as DefaultAvatarIcon,
} from "@heroicons/react/24/outline";
import { HandThumbUpIcon as TrueLikeIcon } from "@heroicons/react/24/solid";

import Avatar from "../Avatar";
import moment from "moment";
import { Board } from "@/types/api";

interface ContentFooterProps {
  position: "side" | "main";
  footerData: Board;
}
/** 2023/05/08 - 게시글 하단 footer - by leekoby */
const ContentFooter: React.FC<ContentFooterProps> = ({ position, footerData }) => {
  if (!footerData) return <></>;
  return (
    <div className="flex justify-between w-full px-3 py-1">
      <div className={` gap-x-1 flex items-center justify-start w-full`}>
        {/* 아바타 프로필  */}
        {/* <DefaultAvatarIcon className={`${position === "side" ? " w-5 h-5 text-sub-500" : "w-7 h-7"} `} /> */}
        <Avatar
          src={footerData.profileImageUrl}
          className={`${position === "side" ? " w-5 h-5 text-sub-500" : "w-7 h-7"} `}
        />
        {/* 닉네임 */}
        <div className="flex flex-col items-start justify-center w-full">
          <span className={`${position === "side" ? "text-xs text-sub-500" : "text-sm font-bold"} cursor-pointer `}>
            {footerData.nickname}
          </span>
          {/* 작성일 */}
          {footerData.createdAt && (
            <time className={`text-xs  text-sub-500`}>{moment(footerData.createdAt).endOf("day").fromNow()}</time>
          )}
        </div>
      </div>
      <div className={`flex gap-x-1 ${position === "main" && "self-end"}`}>
        <button type="button" className={`flex items-center justify-center text-sub-600 gap-x-1`}>
          <ViewIcon
            className={` ${position === "side" ? "text-sub-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
          />
          <span className={`${position === "side" ? "text-[10px] text-sub-500" : "text-xs text-black"} `}>
            {footerData.viewCount}
          </span>
        </button>
        <button type="button" className={`flex text-sub-600 gap-x-1`}>
          {!footerData.liked ? (
            <FalseLikeIcon
              className={` ${position === "side" ? "text-sub-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
            />
          ) : (
            <TrueLikeIcon
              className={` ${position === "side" ? "text-sub-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
            />
          )}
          <span className={`${position === "side" ? "text-[10px] text-sub-500" : "text-xs text-black"} `}>
            {footerData.likeCount}
          </span>
        </button>

        <div className="flex items-center justify-center text-sub-600 gap-x-1">
          <CommentsIcon
            className={` ${position === "side" ? "text-sub-500 w-3 h-3" : "text-black w-4 h-4 ml-2"} cursor-pointer `}
          />
          <div className={`${position === "side" ? "text-[10px] text-sub-500" : "text-xs text-black"} `}>
            {footerData.commentCount}
          </div>
        </div>
      </div>
    </div>
  );
};
export default ContentFooter;
