import PromotionMain from "./PromotionMain";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 홍보 게시판",
};

/** 2023/05/17 - 홍보 게시판 메인 화면 페이지 - by leekoby */

const Page = () => {
  return <PromotionMain />;
};

export default Page;
