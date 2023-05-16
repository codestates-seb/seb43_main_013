import FeedbackMain from "./FeedbackMain";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 피드백 게시판",
};

/** 2023/05/09 - 피드백 게시판 메인 화면 페이지 - by leekoby */
const Page = () => {
  return <FeedbackMain />;
};

export default Page;
