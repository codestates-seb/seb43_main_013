"use client";

import Link from "next/link";
import {
  EyeIcon as ViewIcon,
  HandThumbUpIcon as LikeIcon,
  ChatBubbleLeftRightIcon as CommentsIcon,
} from "@heroicons/react/24/outline";

// util
import { getTimeDiff } from "@/libs/time";

// component
import Avatar from "@/components/Avatar";

// type
import type { BoardOfProfile } from "@/types/api";
interface Props {
  board: BoardOfProfile;
}

const table: { [key: string]: string } = {
  FREEBOARD: "free",
  FEEDBACKBOARD: "feedback",
  JOBBOARD: "job",
  PROMOTIONBOARD: "promotion",
};

/** 2023/05/17 - 프로필 페이지에서 사용하는 게시글 - by 1-blue */
const ProfileBoard: React.FC<Props> = ({ board }) => {
  return (
    <li
      key={board.id}
      className="w-full p-6 bg-sub-100 rounded-lg space-y-4 flex flex-col transition-all shadow-black/20 shadow-sm hover:shadow-black/30 hover:shadow-lg"
    >
      <div className="flex flex-col space-y-2">
        <span className="self-start bg-main-400 text-white px-2 py-1 rounded-sm text-xs">{board.categoryName}</span>

        {/* 제목 */}
        <Link
          href={`/${table[board.boardType]}/${board.id}`}
          className="self-start hover:underline hover:underline-offset-4"
        >
          <h4 className="text-2xl font-bold truncate-1">{board.title}</h4>
        </Link>
      </div>

      {/* 컨텐츠 */}
      <p className="truncate-3" dangerouslySetInnerHTML={{ __html: board.content.replace(/<[^>]*>?/g, "") }} />

      <section className="flex">
        {/* 유저 정보/작성일 */}
        <div className="flex space-x-3">
          <Avatar src={board.profileImageUrl} className="w-12 h-12" href={`/profile/${board.memberId}`} />
          <div className="flex flex-col justify-center">
            <span className="text-sm">{board.nickname}</span>
            <time className="text-xs text-sub-500">{getTimeDiff(board.createdAt)}</time>
          </div>
        </div>

        {/* 조회/좋아요/댓글 수 */}
        <div className="ml-auto flex space-x-4 self-end">
          <div className="flex space-x-1">
            <ViewIcon className="w-5 h-5" />
            <span>{board.viewCount}</span>
          </div>
          <div className="flex space-x-1">
            <CommentsIcon className="w-5 h-5" />
            <span>{board.commentCount}</span>
          </div>
          <div className="flex space-x-1">
            <LikeIcon className="w-5 h-5" />
            <span>{board.likeCount}</span>
          </div>
        </div>
      </section>
    </li>
  );
};

export default ProfileBoard;
