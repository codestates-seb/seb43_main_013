// component
import Navigation from "@/components/Board/Navigation";
import Board from "./Board";

// type
import type { Metadata } from "next";
interface Props {
  params: {
    boardId: number;
  };
}

// TODO: SSR
export const metadata: Metadata = {
  title: "CC | 자유 게시글",
};

/** 2023/05/11 - 자유 게시판 상세 페이지 - by 1-blue */
const Page = ({ params: { boardId } }: Props) => {
  return (
    <>
      {/* 목록 */}
      {/* <Navigation /> */}

      {/* 게시글 상세 내용 */}
      <Board boardId={boardId} />

      {/* 추천/관련 게시글 */}
      <article></article>
    </>
  );
};

export default Page;
