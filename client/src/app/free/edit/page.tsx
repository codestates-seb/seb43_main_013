// component
import Info from "@/components/Board/Form/Info";
import Form from "./Form";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 자유 게시글 수정",
};

// type
interface Props {
  searchParams: { boardId: string };
}

/** 2023/05/10 - 자유 게시글 수정 페이지 - by 1-blue */
const Page = ({ searchParams: { boardId } }: Props) => {
  return (
    <>
      <h1 className="px-4 pt-8 pb-4 text-2xl font-bold">🖌️ 자유 게시글 수정 🖌️</h1>

      <Info />

      <Form boardId={+boardId} />
    </>
  );
};

export default Page;
