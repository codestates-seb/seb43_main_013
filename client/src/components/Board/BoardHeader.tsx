import { useCallback } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { usePathname } from "next/navigation";
import moment from "moment";
import {
  BookmarkIcon as OBookmarkIcon,
  LinkIcon as OLinkIcon,
  EyeIcon as OEyeIcon,
  TrashIcon as OTrashIcon,
  PencilSquareIcon as OPencilSquareIcon,
} from "@heroicons/react/24/outline";
import { BookmarkIcon as SBookmarkIcon } from "@heroicons/react/24/solid";
import { isAxiosError } from "axios";
import { useQueryClient } from "@tanstack/react-query";

// api
import {
  apiCreateBookmark,
  apiDeleteBookmark,
  apiDeleteFeedbackBoard,
  apiDeleteFreeBoard,
  apiDeleteJobBoard,
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

  const queryClient = useQueryClient();

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

  /** 2023/05/17 - 게시글 북마크 - by 1-blue */
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

      // FIXME: 시간 남으면 캐싱 무효화에서 수정하기
      queryClient.invalidateQueries([`${type}Board`, boardId]);
    } catch (error) {
      console.error(error);

      if (isAxiosError(error)) {
        toast({ title: error.response?.data, status: "error" });
      } else {
        toast({ title: "북마크 처리를 실패했습니다.", status: "error" });
      }
    }
  };

  return (
    <>
      {/* 아바타, 북마크, 링크, 수정 삭제 */}
      <section className="flex items-center">
        <Avatar src={profileImageUrl} alt={`${nickname}님의 프로필 이미지`} className="w-16 h-16 object-fill" />

        <button type="button" className="ml-auto" onClick={copyLink}>
          <OLinkIcon className="text-sub-700 w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500" />
        </button>
        <button type="button" className="ml-4">
          {bookmarked ? (
            <SBookmarkIcon
              className="text-sub-700 w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500"
              onClick={onClickBookmark}
            />
          ) : (
            <OBookmarkIcon
              className="text-sub-700 w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500"
              onClick={onClickBookmark}
            />
          )}
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

      {/* 제목 */}
      <section>
        <h1 className="text-2xl font-bold truncate">{title}</h1>
      </section>

      {/* 작성자 이름 / 작성일 */}
      <section className="flex items-center">
        <span className="text-sub-400 after:content-['|'] after:mx-2 after:text-sub-400">{nickname}</span>
        <span className="text-sub-400 text-sm">{moment(createdAt).endOf("day").fromNow()}</span>

        <OEyeIcon className="text-sub-700 ml-auto w-6 h-6" />
        <span className="ml-1 text-sm text-sub-700">{viewCount.toLocaleString()}</span>
      </section>

      {/* 카테고리 || 채널명/구독자수 */}
      <section className="flex">
        {channelName && subscriberCount && (
          <div className="flex flex-col mr-auto">
            {<span className="font-semibold text-xs text-sub-700">채널명: {channelName}</span>}
            {<span className="font-semibold text-xs text-sub-700">구독자: {subscriberCount.toLocaleString()}</span>}
          </div>
        )}

        <div>
          {categoryName && <span className="text-base font-semibold text-main-500">{categoryName}</span>}
          {feedbackCategoryName && (
            <span className="text-base font-semibold text-main-500 before:content-['|'] before:mx-2 before:text-main-400">
              {feedbackCategoryName}
            </span>
          )}
          {jobCategoryName && <span className="text-base font-semibold text-main-500">{jobCategoryName}</span>}
        </div>
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
    </>
  );
};

export default BoardHeader;
