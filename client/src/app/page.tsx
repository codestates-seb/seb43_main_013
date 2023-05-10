import type { Metadata } from "next";
import HomeMain from "./HomeMain";

/** 2023/05/04 - 메타데이터 - by 1-blue */
export const metadata: Metadata = {
  title: "CC",
  description: "CC의 로그인 페이지입니다.",
};

/** 2023/05/04 - 메인 페이지 컴포넌트 - by 1-blue */
const Home = () => {
  return <HomeMain />;
};

export default Home;
