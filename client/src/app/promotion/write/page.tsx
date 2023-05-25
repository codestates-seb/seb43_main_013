// component
import Info from "@/components/Board/Form/Info";
import Form from "./Form";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 홍보 게시글 작성",
};

/** 2023/05/09 - 홍보 게시글 작성 페이지 - by 1-blue */
const Page = () => {
  return (
    <>
      <h1 className="px-4 pt-8 pb-4 text-2xl font-bold">✏️ 홍보 게시글 작성 ✏️</h1>

      <Info />

      <Form />
    </>
  );
};

export default Page;
