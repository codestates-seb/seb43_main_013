import { useCallback } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { usePathname } from "next/navigation";
import {
  BookmarkIcon as OBookmarkIcon,
  LinkIcon as OLinkIcon,
  EyeIcon as OEyeIcon,
  TrashIcon as OTrashIcon,
  PencilSquareIcon as OPencilSquareIcon,
} from "@heroicons/react/24/outline";
import { BookmarkIcon as SBookmarkIcon } from "@heroicons/react/24/solid";

// lib
import { getTimeDiff } from "@/libs/time";

// api
import {
  apiDeleteFeedbackBoard,
  apiDeleteFreeBoard,
  apiDeleteJobBoard,
  apiDeleteNoticeBoard,
  apiDeletePromotionBoard,
} from "@/apis";

// store
import { useLoadingStore } from "@/store";
import { useMemberStore } from "@/store/useMemberStore";

// hook
import useCustomToast from "@/hooks/useCustomToast";

// component
import Avatar from "@/components/Avatar";

// type
import type { Board, BoardType, DetailTag } from "@/types/api";
interface Props extends Board {
  type: BoardType;
  boardId: number;
  tags?: DetailTag[];
  categoryName?: string;
  feedbackCategoryName?: string;
  jobCategoryName?: string;
  channelName?: string;
  subscriberCount?: number;
}

/** 2023/05/11 - 보드 상단 컴포넌트 - by 1-blue */
const BoardHeader: React.FC<Props> = ({
  type,
  boardId,
  title,
  tags,
  memberId,
  nickname,
  createdAt,
  profileImageUrl,
  viewCount,
  categoryName,
  feedbackCategoryName,
  jobCategoryName,
  channelName,
  subscriberCount,
  bookmarked,
}) => {
  const toast = useCustomToast();
  const router = useRouter();
  const pathname = usePathname();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();

  /** 2023/05/12 - copy clipboard - by 1-blue */
  const copyLink = useCallback(() => {
    navigator.clipboard
      .writeText(window.location.origin + pathname)
      .then(() => toast({ title: "링크를 복사했습니다.", status: "success" }));
  }, []);

  /** 2023/05/12 - 게시판 삭제 핸들러 - by 1-blue */
  const onDeleteBoard = useCallback(async () => {
    if (!confirm("정말 게시판을 삭제하시겠습니까?")) return;

    try {
      loading.start();

      switch (type) {
        case "feedback":
          await apiDeleteFeedbackBoard({ feedbackBoardId: boardId });
          break;
        case "free":
          await apiDeleteFreeBoard({ freeBoardId: boardId });
          break;
        case "job":
          await apiDeleteJobBoard({ jobBoardId: boardId });
          break;
        case "promotion":
          await apiDeletePromotionBoard({ promotionBoardId: boardId });
          break;
        case "notice":
          await apiDeleteNoticeBoard({ noticeId: boardId });
          break;
      }

      toast({ title: "게시판을 삭제했습니다.\n메인 페이지로 이동됩니다!", status: "success" });

      router.replace("/");
    } catch (error) {
      console.error(error);

      toast({ title: "게시판 삭제에 실패했습니다.\n잠시후에 다시 시도해주세요!", status: "error" });
    } finally {
      loading.end();
    }
  }, [boardId, loading]);

  return (
    <div className="space-y-4">
      <section className="flex justify-between">
        <div>
          {categoryName && <span className="text-xl font-semibold text-main-500">{categoryName}</span>}
          {feedbackCategoryName && (
            <span className="text-xl font-semibold text-main-500 before:content-['|'] before:mx-2 before:text-main-400">
              {feedbackCategoryName}
            </span>
          )}
          {jobCategoryName && <span className="text-xl font-semibold text-main-500">{jobCategoryName}</span>}
        </div>

        <div className="flex items-center">
          <OEyeIcon className="text-sub-700 ml-auto w-6 h-6" />
          <span className="ml-1 text-sm text-sub-700">{viewCount.toLocaleString()}</span>
        </div>
      </section>

      {/* 제목 */}
      <section className="flex items-center">
        <h1 className="text-4xl font-bold truncate-1">{title}</h1>

        <button type="button" className="ml-auto" onClick={copyLink}>
          <OLinkIcon className="text-sub-700 w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500" />
        </button>

        {member?.memberId === memberId && (
          <>
            <Link href={`/${type}/edit?boardId=${boardId}`} className="ml-4">
              <OPencilSquareIcon className="text-sub-700 w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500" />
            </Link>
            <button type="button" className="ml-4" onClick={onDeleteBoard}>
              <OTrashIcon className="text-sub-700 w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500" />
            </button>
          </>
        )}
      </section>

      {/* 아바타, 북마크, 링크, 수정 삭제 */}
      {/* 작성자 이름 / 작성일 */}
      <section className="flex items-center space-x-3">
        <Avatar
          src={profileImageUrl}
          alt={`${nickname}님의 프로필 이미지`}
          className="w-12 h-12 object-fill"
          href={`/profile/${memberId}`}
        />

        <div className="flex flex-col">
          <span className="text-sub-600">{nickname}</span>
          <span className="text-sub-400 text-sm">{getTimeDiff(createdAt)}</span>
        </div>
      </section>

      {/* 카테고리 || 채널명/구독자수 */}
      <section className="flex">
        {channelName && subscriberCount && (
          <div className="flex flex-col mr-auto">
            {<span className="font-semibold text-xs text-sub-700">채널명: {channelName}</span>}
            {<span className="font-semibold text-xs text-sub-700">구독자: {subscriberCount.toLocaleString()}</span>}
          </div>
        )}
      </section>

      {/* 태그 */}
      {tags && (
        <ul className="flex space-x-2 flex-wrap">
          {tags.map((tag) => (
            <li
              key={tag.tagName}
              className="px-2 py-1 mt-1 text-xs font-bold text-main-400 border-2 border-main-400 rounded-lg"
            >
              {tag.tagName}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default BoardHeader;
