import FreeMain from "./FreeMain";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 자유 게시판",
};

/** 2023/05/09 - 자유 게시판 메인 화면 페이지- by leekoby */
const Page = () => {
  return <FreeMain />;
};

export default Page;
