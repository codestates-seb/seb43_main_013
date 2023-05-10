// component
import Signup from "./Signup";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 회원가입",
};

/** 2023/05/10 - 회원가입 페이지 - by Kadesti */
const Page = () => {
  return <Signup />;
};

export default Page;
