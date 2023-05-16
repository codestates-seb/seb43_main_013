import { useQueryClient } from "@tanstack/react-query";
import { useToast } from "@chakra-ui/react";
import {
  HandThumbUpIcon as OLikeIcon,
  ChatBubbleOvalLeftEllipsisIcon as OCommentIcon,
} from "@heroicons/react/24/outline";
import { HandThumbUpIcon as SLikeIcon } from "@heroicons/react/24/solid";

// api
import { apiCreateLikeOfPost, apiDeleteLikeOfPost } from "@/apis";

// type
import type { BoardType, FreeBoard } from "@/types/api";
interface Props extends Pick<FreeBoard, "commentCount" | "likeCount"> {
  type: BoardType;
  boardId: number;
}

/** 2023/05/11 - 게시판 하단 컴포넌트 - by 1-blue */
const BoardFooter: React.FC<Props> = ({ type, boardId, commentCount, likeCount }) => {
  const toast = useToast();

  const queryClient = useQueryClient();

  /** 2023/05/17 - 게시글 좋아요 - by 1-blue */
  const onClickLike = async () => {
    try {
      // TODO: 좋아요 여부에 따른 조건부 처리
      if (true) {
        await apiCreateLikeOfPost(type, { boardId });
      } else {
        await apiDeleteLikeOfPost(type, { boardId });
      }

      // TODO: 좋아요 개수 및 상태 변경
      // queryClient()

      toast({
        description: "좋아요를 눌렀습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });
    } catch (error) {
      console.error(error);

      toast({
        description: "이미 좋아요를 누른 게시글입니다.",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    }
  };

  return (
    <section className="flex justify-between items-center">
      <div className="flex space-x-1">
        <button type="button">
          {/* FIXME: 조건부 처리 */}
          {true ? <OCommentIcon className="w-5 h-5" /> : <SLikeIcon className="w-5 h-5" />}
        </button>
        <span className="text-sm">( {commentCount.toLocaleString()} )</span>
      </div>

      <div className="flex space-x-1">
        <button type="button" onClick={onClickLike}>
          <OLikeIcon className="w-5 h-5 hover:text-main-400 active:text-main-500 hover:stroke-2" />
        </button>
        <span className="text-sm">( {likeCount.toLocaleString()} )</span>
      </div>
    </section>
  );
};

export default BoardFooter;
