import { useQueryClient } from "@tanstack/react-query";
import {
  HandThumbUpIcon as OLikeIcon,
  ChatBubbleOvalLeftEllipsisIcon as OCommentIcon,
} from "@heroicons/react/24/outline";
import { HandThumbUpIcon as SLikeIcon } from "@heroicons/react/24/solid";

// api
import { apiCreateLikeOfPost, apiDeleteLikeOfPost } from "@/apis";

// hook
import useCustomToast from "@/hooks/useCustomToast";
import { useMemberStore } from "@/store/useMemberStore";

// type
import type { ApiFetchFeedbackBoardResponse, BoardType, FreeBoard } from "@/types/api";
interface Props extends Pick<FreeBoard, "commentCount" | "likeCount"> {
  type: BoardType;
  boardId: number;
  liked?: boolean;
}

/** 2023/05/11 - 게시판 하단 컴포넌트 - by 1-blue */
const BoardFooter: React.FC<Props> = ({ type, boardId, commentCount, likeCount, liked }) => {
  const toast = useCustomToast();
  const { member } = useMemberStore();

  const queryClient = useQueryClient();

  /** 2023/05/17 - 게시글 좋아요 - by 1-blue */
  const onClickLike = async () => {
    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    try {
      if (liked) {
        await apiDeleteLikeOfPost(type, { boardId });
        toast({ title: "좋아요를 제거했습니다.", status: "success" });
      } else {
        await apiCreateLikeOfPost(type, { boardId });
        toast({ title: "좋아요를 눌렀습니다.", status: "success" });
      }

      queryClient.setQueryData<ApiFetchFeedbackBoardResponse>(
        [`${type}Board`, boardId],
        (prev) => prev && { ...prev, liked: !liked, likeCount: liked ? prev.likeCount - 1 : prev.likeCount + 1 },
      );
    } catch (error) {
      console.error(error);

      toast({ title: "이미 좋아요를 누른 게시글입니다.", status: "error" });
    }
  };

  return (
    <section className="flex justify-between items-center">
      {likeCount === undefined ? null : (
        <div className="flex space-x-1">
          <button type="button">
            <OCommentIcon className="text-sub-700 w-5 h-5" />
          </button>
          <span className="text-sm">( {commentCount.toLocaleString()} )</span>
        </div>
      )}

      {likeCount === undefined ? null : (
        <div className="flex space-x-1">
          <button type="button" onClick={onClickLike}>
            {liked ? (
              <SLikeIcon className="w-5 h-5 text-sub-700 hover:text-main-400 active:text-main-500 hover:stroke-2" />
            ) : (
              <OLikeIcon className="w-5 h-5 text-sub-700 hover:text-main-400 active:text-main-500 hover:stroke-2" />
            )}
          </button>
          <span className="text-sm">( {likeCount.toLocaleString()} )</span>
        </div>
      )}
    </section>
  );
};

export default BoardFooter;
