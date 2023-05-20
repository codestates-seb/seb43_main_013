"use client";

import Link from "next/link";
import moment from "moment";
import {
  EyeIcon as ViewIcon,
  HandThumbUpIcon as LikeIcon,
  ChatBubbleLeftRightIcon as CommentsIcon,
} from "@heroicons/react/24/outline";

// component
import Avatar from "@/components/Avatar";

// type
import type { BoardOfProfile } from "@/types/api";
interface Props {
  board: BoardOfProfile;
}

/** 2023/05/17 - 프로필 페이지에서 사용하는 게시글 - by 1-blue */
const ProfileBoard: React.FC<Props> = ({ board }) => {
  return (
    <li
      key={board.id}
      className="w-full p-6 bg-sub-100 rounded-lg space-y-3 flex flex-col transition-all hover:shadow-xl"
    >
      {/* 카테고리 */}
      <Link href={`/TODO:`} className="self-start bg-main-400 text-white px-2 py-1 rounded-sm text-xs">
        {board.categoryName}
      </Link>

      {/* 제목 */}
      <Link href={`/free/${board.id}`} className="self-start hover:underline hover:underline-offset-4">
        <h4 className="text-2xl font-bold">{board.title}</h4>
      </Link>

      {/* 컨텐츠 */}
      <div>대충 게시글 내용 - 1</div>
      {/* <div dangerouslySetInnerHTML={{ __html: board.content }} /> */}

      <section className="flex">
        {/* 유저 정보/작성일 */}
        <div className="flex space-x-2">
          <Avatar src={board.profileImageUrl} className="w-12 h-12" />
          <div className="flex flex-col justify-center">
            <span className="text-sm">{board.nickname}</span>
            <time className="text-xs text-sub-500">{moment(board.createdAt).endOf("day").fromNow()}</time>
          </div>
        </div>

        {/* 조회/좋아요/댓글 수 */}
        <div className="ml-auto flex space-x-2 self-end">
          <button type="button" className="flex space-x-1">
            <ViewIcon className="w-5 h-5" />
            <span>{board.viewCount}</span>
          </button>
          <button type="button" className="flex space-x-1">
            <LikeIcon className="w-5 h-5" />
            <span>{board.likeCount}</span>
          </button>
          <button type="button" className="flex space-x-1">
            <CommentsIcon className="w-5 h-5" />
            <span>{board.commentCount}</span>
          </button>
        </div>
      </section>
    </li>
  );
};

export default ProfileBoard;
