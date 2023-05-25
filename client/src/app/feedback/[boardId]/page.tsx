// component
import Navigation from "@/components/Board/Navigation";
import Board from "./Board";

// type
import type { Metadata } from "next";
import { apiSSRFetchFeedbackBoard } from "@/apis/ssr";
import { getMetadata, getYoutubeThumbnail } from "@/libs";
interface Props {
  params: {
    boardId: number;
  };
}

/** 2023/05/25 - 메타데이터 - by 1-blue */
export const generateMetadata = async ({ params: { boardId } }: Props): Promise<Metadata> => {
  const initialData = await apiSSRFetchFeedbackBoard({ feedbackBoardId: boardId });

  return getMetadata({
    title: initialData.title,
    description: initialData.content.replace(/<[^>]*>?/g, "") || "존재하지 않는 피드백 게시글입니다.",
    images: initialData.link ? [getYoutubeThumbnail(initialData.link)] : undefined,
  });
};

/** 2023/05/12 - 피드백 게시판 상세 페이지 - by 1-blue */
const Page = async ({ params: { boardId } }: Props) => {
  const initialData = await apiSSRFetchFeedbackBoard({ feedbackBoardId: boardId });

  return (
    <>
      {/* 목록 */}
      {/* <Navigation /> */}

      {/* 게시글 상세 내용 */}
      <Board boardId={boardId} initialData={initialData} />

      {/* 추천/관련 게시글 */}
      <article></article>
    </>
  );
};

export default Page;
