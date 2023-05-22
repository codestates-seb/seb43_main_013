import { useCallback } from "react";
import {
  BookmarkIcon as OBookmarkIcon,
  LinkIcon as OLinkIcon,
  ChatBubbleLeftEllipsisIcon as OCommentIcon,
  HeartIcon as OHeartIcon,
} from "@heroicons/react/24/outline";
import { BookmarkIcon as SBookmarkIcon, HeartIcon as SHeartIcon } from "@heroicons/react/24/solid";
import { usePathname } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { twMerge } from "tailwind-merge";

// api
import { apiCreateBookmark, apiCreateLikeOfPost, apiDeleteBookmark, apiDeleteLikeOfPost } from "@/apis";

// hook
import useCustomToast from "@/hooks/useCustomToast";
import { useMemberStore } from "@/store/useMemberStore";

// type
import type { ApiFetchFeedbackBoardResponse, BoardType } from "@/types/api";
interface Props {
  type: BoardType;
  boardId: number;
  bookmarked?: boolean;
  liked?: boolean;
  commentCount: number;
  likeCount: number;
}

/** 2023/05/21 - board 사이드 버튼 컴포넌트 - by 1-blue */
const BoardASide: React.FC<Props> = ({ type, boardId, bookmarked, liked, commentCount, likeCount }) => {
  const toast = useCustomToast();
  const pathname = usePathname();
  const { member } = useMemberStore();

  const queryClient = useQueryClient();

  /** 2023/05/21 - copy clipboard - by 1-blue */
  const copyLink = useCallback(() => {
    navigator.clipboard
      .writeText(window.location.origin + pathname)
      .then(() => toast({ title: "링크를 복사했습니다.", status: "success" }));
  }, []);

  /** 2023/05/21 - 게시글 북마크 - by 1-blue */
  const onClickBookmark = async () => {
    if (!member) {
      return toast({ title: "로그인후에 접근해주세요!", status: "error" });
    }

    try {
      if (bookmarked) {
        await apiDeleteBookmark(type, { boardId });
        toast({ title: "북마크를 제거했습니다.", status: "success" });
      } else {
        await apiCreateBookmark(type, { boardId });
        toast({ title: "북마크를 눌렀습니다.", status: "success" });
      }

      queryClient.setQueryData<ApiFetchFeedbackBoardResponse>(
        [`${type}Board`, boardId],
        (prev) => prev && { ...prev, bookmarked: !bookmarked },
      );
    } catch (error) {
      console.error(error);

      if (isAxiosError(error)) {
        toast({ title: error.response?.data, status: "error" });
      } else {
        toast({ title: "북마크 처리를 실패했습니다.", status: "error" });
      }
    }
  };

  /** 2023/05/21 - 게시글 좋아요 - by 1-blue */
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
    <section className="absolute top-0 -right-20 h-full hidden 2xl:inline-block">
      <ul className="h-full">
        <div className="sticky top-4 space-y-2 px-2">
          {/* 좋아요 */}
          <li className="flex flex-col items-center ">
            <button
              type="button"
              onClick={onClickLike}
              className={twMerge(
                "group border-2 transition-all p-2.5 rounded-full",
                liked ? "border-red-300 hover:border-red-400" : "border-sub-300 hover:border-sub-400",
              )}
            >
              {liked ? (
                <SHeartIcon className="w-6 h-6 text-red-400 transition-all group-hover:text-red-500 group-active:text-red-500 group-hover:stroke-2" />
              ) : (
                <OHeartIcon className="w-6 h-6 text-sub-400 transition-all group-hover:text-sub-500 group-active:text-sub-500 group-hover:stroke-2" />
              )}
            </button>
            <span className="text-base font-bold text-sub-400">{likeCount.toLocaleString()}</span>
          </li>

          {/* 댓글 */}
          <li className="flex flex-col items-center">
            <button
              type="button"
              className="group border-2 border-sub-300 transition-all hover:border-sub-400 p-2.5 rounded-full"
            >
              <OCommentIcon className="text-sub-400 w-6 h-6 transition-all group-hover:text-sub-500 group-active:text-sub-500" />
            </button>
            <span className="text-base font-bold text-sub-400">{commentCount.toLocaleString()}</span>
          </li>

          <hr className="border border-sub-300" />

          {/* 북마크 */}
          <li>
            <button
              type="button"
              className={twMerge(
                "group border-2 transition-all p-2.5 rounded-full",
                bookmarked ? "border-blue-300 hover:border-blue-400" : "border-sub-300 hover:border-sub-400",
              )}
              onClick={onClickBookmark}
            >
              {bookmarked ? (
                <SBookmarkIcon className="w-6 h-6 text-blue-400 transition-all group-hover:text-blue-500 group-active:text-blue-500" />
              ) : (
                <OBookmarkIcon className="w-6 h-6 text-sub-400 transition-all group-hover:text-sub-500 group-active:text-sub-500" />
              )}
            </button>
          </li>

          {/* 클립보드 */}
          <li>
            <button
              type="button"
              className="group border-2 border-sub-300 transition-all hover:border-sub-400 p-2.5 rounded-full"
              onClick={copyLink}
            >
              <OLinkIcon className="text-sub-400 w-6 h-6 transition-all group-hover:text-sub-500 group-hover:stroke-2 group-active:text-sub-500" />
            </button>
          </li>
        </div>
      </ul>
    </section>
  );
};

export default BoardASide;
