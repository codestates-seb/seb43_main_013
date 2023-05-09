// component
import Form from "./Form";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 구인구직 게시글 작성",
};

/** 2023/05/09 - 구인구직 게시글 작성 페이지 - by 1-blue */
const Page = () => {
  return (
    <>
      <h1 className="p-4 text-2xl font-bold">✏️ 구인구직 게시글 작성 ✏️</h1>

      <Form />
    </>
  );
};

export default Page;
