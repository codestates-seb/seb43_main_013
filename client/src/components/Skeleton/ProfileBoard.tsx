import {
  EyeIcon as ViewIcon,
  HandThumbUpIcon as LikeIcon,
  ChatBubbleLeftRightIcon as CommentsIcon,
} from "@heroicons/react/24/outline";

// component
import Skeleton from ".";

/** 2023/05/17 - 프로필 게시판 스켈레톤 UI 컴포넌트 - by 1-blue */
const ProfileBoard = () => {
  return (
    <li className="w-full p-6 bg-sub-100 rounded-lg space-y-3 flex flex-col transition-colors hover:bg-sub-200">
      {/* 카테고리 */}
      <Skeleton.Square className="self-start px-2 py-1 rounded-sm" />

      {/* 제목 */}
      <Skeleton.Square className="self-start w-2/3 h-10" />

      {/* 컨텐츠 */}
      <div className="space-y-1">
        <Skeleton.Square className="w-1/2" />
        <Skeleton.Square className="w-1/3" />
      </div>

      <section className="flex">
        {/* 유저 정보/작성일 */}
        <div className="flex space-x-2">
          <Skeleton.Circle className="w-12 h-12" />
          <div className="flex flex-col justify-center space-y-1">
            <Skeleton.Square className="h-4" />
            <Skeleton.Square className="h-4" />
          </div>
        </div>

        {/* 조회/좋아요/댓글 수 */}
        <div className="ml-auto flex space-x-2 self-end">
          <button type="button" className="flex space-x-1">
            <ViewIcon className="w-5 h-5" />
            <Skeleton.Square className="w-8 h-5" />
          </button>
          <button type="button" className="flex space-x-1">
            <LikeIcon className="w-5 h-5" />
            <Skeleton.Square className="w-8 h-5" />
          </button>
          <button type="button" className="flex space-x-1">
            <CommentsIcon className="w-5 h-5" />
            <Skeleton.Square className="w-8 h-5" />
          </button>
        </div>
      </section>
    </li>
  );
};

export default ProfileBoard;
