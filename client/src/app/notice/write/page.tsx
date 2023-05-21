// component
import Form from "./Form";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 공지사항 게시글 작성",
};

/** 2023/05/20 - 공지사항 게시글 작성 페이지 - by leekoby */
const Page = () => {
  return (
    <>
      <h1 className="p-4 text-2xl font-bold">✏️ 공지사항 게시글 작성 ✏️</h1>

      <Form />
    </>
  );
};

export default Page;
