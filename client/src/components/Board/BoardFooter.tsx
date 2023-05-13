import {
  HandThumbUpIcon as OLikeIcon,
  ChatBubbleOvalLeftEllipsisIcon as OCommentIcon,
} from "@heroicons/react/24/outline";
// import { HandThumbUpIcon as SLikeIcon, ChatBubbleOvalLeftEllipsisIcon as SCommentIcon } from "@heroicons/react/24/solid";

// type
import type { FreeBoard } from "@/types/api";
interface Props extends Pick<FreeBoard, "commentCount" | "likeCount"> {}

/** 2023/05/11 - 게시판 하단 컴포넌트 - by 1-blue */
const BoardFooter: React.FC<Props> = ({ commentCount, likeCount }) => {
  return (
    <section className="flex justify-between items-center">
      <div className="flex space-x-1">
        <button type="button">
          <OCommentIcon className="w-5 h-5" />
        </button>
        <span className="text-sm">( {commentCount.toLocaleString()} )</span>
      </div>

      <div className="flex space-x-1">
        <button type="button">
          <OLikeIcon className="w-5 h-5 hover:text-main-400 active:text-main-500 hover:stroke-2" />
        </button>
        <span className="text-sm">( {likeCount.toLocaleString()} )</span>
      </div>
    </section>
  );
};

export default BoardFooter;
