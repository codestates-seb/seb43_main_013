import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";

// 임의로 정의한 카테고리
const dummyNormalCategories = [
  "먹방",
  "게임",
  "스포츠",
  "뷰티",
  "이슈",
  "음악",
  "쿠킹",
  "동물",
  "영화",
  "드라마",
  "디자인",
  "코딩",
];
const dummyFeedbackCategories = ["영상", "채널", "썸네일"];
const dummyJobCategories = ["회사", "편집자", "pd", "작가"];

/** 2023/05/09 - 카테고리 요청 - by 1-blue */
const handler: NextApiHandler = async (req, res) => {
  try {
    if (req.method === "GET") {
      const { type } = req.query;

      await timer(1500);

      if (type === "normal") {
        return res.status(200).json({ categories: dummyNormalCategories });
      }
      if (type === "feedback") {
        return res.status(200).json({ categories: dummyFeedbackCategories });
      }
      if (type === "job") {
        return res.status(200).json({ categories: dummyJobCategories });
      }
    }
  } catch (error) {
    console.error("/api/categories error >> ", error);

    return res.status(500).json({ message: "서버측 문제입니다.\n잠시후에 다시 시도해주세요!" });
  }
};

export default handler;
