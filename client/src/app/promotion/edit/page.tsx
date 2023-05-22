// component
import Form from "./Form";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 홍보 게시글 수정",
};

// type
interface Props {
  searchParams: { boardId: string };
}

/** 2023/05/10 - 홍보 게시글 수정 페이지 - by 1-blue */
const Page = ({ searchParams: { boardId } }: Props) => {
  return (
    <>
      <h1 className="p-4 text-2xl font-bold">✏️ 홍보 게시글 수정 ✏️</h1>

      <Form boardId={+boardId} />
    </>
  );
};

export default Page;
