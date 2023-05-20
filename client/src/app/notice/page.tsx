import type { Metadata } from "next";
import NoticeMain from "./NoticeMain";

/** 2023/05/20 - 메타데이터 - by leekoby */
export const metadata: Metadata = {
  title: "CC",
  description: "CC의 공지 페이지입니다.",
};

/** 2023/05/20- 메인 페이지 컴포넌트 - by leekoby */
const Home = () => {
  return <NoticeMain />;
};

export default Home;
