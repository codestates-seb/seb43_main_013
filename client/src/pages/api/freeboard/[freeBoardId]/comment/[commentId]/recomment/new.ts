import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiCreateRecommentResponse } from "@/types/api";

/** 2023/05/11 - 답글 C - by 1-blue */
const handler: NextApiHandler<ApiCreateRecommentResponse> = async (req, res) => {
  timer(1000);

  try {
    // 답글 생성
    if (req.method === "POST") {
      return res.status(201).json({ recommentId: 1 });
    }
  } catch (error) {
    console.error("/api/feedbackboard/[feedbackBoardId]/comment/[commentId]/recomment/new error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
