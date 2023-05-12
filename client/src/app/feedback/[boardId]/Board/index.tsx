"use client";

import Link from "next/link";

// hook
import { useFetchFeedbackBoard } from "@/hooks/query";

// component
import BoardHeader from "@/components/Board/BoardHeader";
import BoardThumbnail from "@/components/Board/BoardThumbnail";
import BoardContent from "@/components/Board/BoardContent";
import BoardFooter from "@/components/Board/BoardFooter";
import BoardComments from "@/components/Board/BoardComments";
import BoardCommentForm from "@/components/Board/BoardCommentForm";
import Skeleton from "@/components/Skeleton";

// type
interface Props {
  boardId: number;
}

/** 2023/05/11 - 게시판 내용 - by 1-blue */
const Board: React.FC<Props> = ({ boardId }) => {
  const { data, isLoading } = useFetchFeedbackBoard({ feedbackBoardId: boardId });

  // Skeleton UI
  if (isLoading) return <Skeleton.Board />;
  // TODO: 에러 페이지로 이동시키기
  if (!data) return <span>Error 페이지...</span>;

  return (
    <article className="p-8 space-y-2 bg-white shadow-lg m-4 rounded-md">
      {/* 작성자 정보 / 클립보드/북마크  */}
      {/* 제목 */}
      {/* 이름/작성일 */}
      {/* 태그 */}
      <BoardHeader {...data} />

      {/* 라인 */}
      <div>
        <hr className="h-0.5 bg-gray-200 my-6" />
      </div>

      {/* 영상 링크 */}
      {data.link.includes("https://www.youtube.com") && (
        <Link
          href={data.link}
          target="_blank"
          referrerPolicy="no-referrer"
          className="font-bold underline-offset-4 hover:underline hover:text-main-500"
        >
          유튜브 영상
        </Link>
      )}
      {/* 썸네일/영상 */}
      <BoardThumbnail url={data.link} />

      {/* 내용 */}
      <BoardContent content={data.content} />

      {/* 댓글 정보 / 좋아요 정보 */}
      <BoardFooter commentCount={data.commentCount} likeCount={data.likeCount} />

      {/* 라인 */}
      <div>
        <hr className="h-0.5 bg-gray-200 my-4" />
      </div>

      {/* 댓글과 답글 */}
      <BoardComments type="feedback" boardId={boardId} />

      {/* 댓글폼 */}
      <BoardCommentForm type="feedback" boardId={boardId} />
    </article>
  );
};

export default Board;
