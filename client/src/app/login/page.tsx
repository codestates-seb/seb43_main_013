import type { Metadata } from "next";
import Login from "./Login";

/** 2023/05/11 - 메타데이터 - by Kadesti */
export const metadata: Metadata = {
  title: "CC",
  description: "CC의 로그인 페이지입니다.",
};

/** 2023/05/11 - 메인 페이지 컴포넌트 - by Kadesti */
const Page = () => {
  return <Login />;
};

export default Page;
