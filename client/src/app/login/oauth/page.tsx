import type { Metadata } from "next";
import OAuthLogin from "./OAuthLogin";

/** 2023/05/21 - 메타데이터 - by Kadesti */
export const metadata: Metadata = {
  title: "CC",
  description: "CC의 OAuth 로그인 페이지입니다.",
};

/** 2023/05/21 - OAuth 로그인 - by Kadesti */
const Page = () => {
  return <OAuthLogin />;
};

export default Page;
