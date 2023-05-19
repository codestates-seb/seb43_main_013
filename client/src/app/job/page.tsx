import JobMain from "./JobMain";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 구인구직 게시판",
};

/** 2023/05/12 - 구인구직 게시판 메인 화면 페이지 - by leekoby */
const Page = () => {
  return <JobMain />;
};

export default Page;
