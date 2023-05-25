"use client";

import { notFound } from "next/navigation";

// hook
import { useFetchNoticeBoard } from "@/hooks/query";

// component
import BoardHeader from "@/components/Board/BoardHeader";
import BoardContent from "@/components/Board/BoardContent";
import BoardFooter from "@/components/Board/BoardFooter";
import BoardComments from "@/components/Board/BoardComments";
import BoardCommentForm from "@/components/Board/BoardCommentForm";
import Skeleton from "@/components/Skeleton";

// type
interface Props {
  boardId: number;
}

/** 2023/05/20 - 공지사항 게시글 내용 - by leekoby */
const Board: React.FC<Props> = ({ boardId }) => {
  const { data, isLoading } = useFetchNoticeBoard({ noticeId: boardId });

  // Skeleton UI
  if (isLoading) return <Skeleton.Board />;
  if (!data) return <Skeleton.Board />;
  // 에러
  if (!data && !isLoading) return notFound();

  return (
    <article className="p-8 space-y-2 bg-white shadow-lg m-4 rounded-md">
      {/* 작성자 정보 / 클립보드/북마크  */}
      {/* 제목 */}
      {/* 이름/작성일 */}
      {/* 태그 */}
      <BoardHeader type="notice" boardId={boardId} {...data} />

      {/* 라인 */}
      <div>
        <hr className="h-0.5 bg-sub-200 my-6" />
      </div>

      {/* 썸네일/영상 */}
      {/* 내용 */}
      <BoardContent content={data.content} />

      {/* 댓글 정보 / 좋아요 정보 */}
      <BoardFooter
        type="notice"
        boardId={boardId}
        commentCount={data.commentCount}
        likeCount={data.likeCount}
        liked={data.liked}
      />

      {/* 라인 */}
      <div>
        <hr className="h-0.5 bg-sub-200 my-4" />
      </div>

      {/* 댓글과 답글 */}
      {/* <BoardComments type="notice" boardId={boardId} /> */}

      {/* 댓글폼 */}
      {/* <BoardCommentForm type="notice" boardId={boardId} /> */}
    </article>
  );
};

export default Board;
