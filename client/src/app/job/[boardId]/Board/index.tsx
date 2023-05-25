"use client";

import { notFound } from "next/navigation";

// hook
import { useFetchJobBoard } from "@/hooks/query";

// component
import BoardHeader from "@/components/Board/BoardHeader";
import BoardContent from "@/components/Board/BoardContent";
import BoardFooter from "@/components/Board/BoardFooter";
import BoardComments from "@/components/Board/BoardComments";
import BoardCommentForm from "@/components/Board/BoardCommentForm";
import Skeleton from "@/components/Skeleton";
import BoardASide from "@/components/Board/BoardASide";
import BoardHashtag from "@/components/Board/BoardHashtag";
import { ApiFetchJobBoardResponse } from "@/types/api";

// type
interface Props {
  boardId: number;
  initialData?: ApiFetchJobBoardResponse;
}

/** 2023/05/11 - 게시판 내용 - by 1-blue */
const Board: React.FC<Props> = ({ boardId, initialData }) => {
  const { data, isLoading } = useFetchJobBoard({ jobBoardId: boardId, initialData });

  // Skeleton UI
  if (isLoading) return <Skeleton.Board />;
  if (!data) return <Skeleton.Board />;
  // 존재하지 않는 게시판
  if (!data && !isLoading) return notFound();

  return (
    <article className="p-8 space-y-2 bg-white shadow-black/40 shadow-sm my-12 mx-4 rounded-md relative">
      {/* 작성자 정보 / 클립보드/북마크  */}
      {/* 제목 */}
      {/* 이름/작성일 */}
      {/* 태그 */}
      <BoardHeader type="job" boardId={boardId} {...data} />

      {/* 라인 */}
      <div>
        <hr className="h-0.5 bg-sub-200 my-6" />
      </div>

      {/* 썸네일/영상 */}
      {/* 내용 */}
      <BoardContent content={data.content} />

      {/* 해시태그 */}
      <BoardHashtag content={data.content} />

      {/* 댓글 정보 / 좋아요 정보 */}
      <BoardFooter
        type="job"
        liked={data.liked}
        boardId={boardId}
        likeCount={data.likeCount}
        commentCount={data.commentCount}
      />

      {/* 라인 */}
      <div>
        <hr className="h-0.5 bg-sub-200 my-4" />
      </div>

      {/* 댓글과 답글 */}
      <BoardComments type="job" boardId={boardId} />

      {/* 댓글폼 */}
      <BoardCommentForm type="job" boardId={boardId} />

      {/* 북마크 사이드 버튼 */}
      <BoardASide
        type="job"
        boardId={boardId}
        bookmarked={data.bookmarked}
        commentCount={data.commentCount}
        likeCount={data.likeCount}
        liked={data.liked}
      />
    </article>
  );
};

export default Board;
