import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiCreateFeedbackBoardRequest, ApiCreateFeedbackBoardResponse } from "@/types/api";

/** 2023/05/10 - 피드백 게시글 - by 1-blue */
const handler: NextApiHandler<ApiCreateFeedbackBoardResponse> = async (req, res) => {
  await timer(3000);

  try {
    // 피드백 게시글 생성
    if (req.method === "POST") {
      const body = req.body as ApiCreateFeedbackBoardRequest;

      return res.status(200).json({ ...body, feedbackBoardId: 1 });
    }
  } catch (error) {
    console.error("/api/feedbackboard/new error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
