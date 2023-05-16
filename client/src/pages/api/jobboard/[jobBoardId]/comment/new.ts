import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiCreateCommentResponse } from "@/types/api";

/** 2023/05/11 - 댓글 CUD - by 1-blue */
const handler: NextApiHandler<ApiCreateCommentResponse> = async (req, res) => {
  timer(1000);

  try {
    // 댓글 생성
    if (req.method === "POST") {
      return res.status(201).json({ commentId: 1 });
    }
  } catch (error) {
    console.error("/api/jobboard/[jobBoardId]/comment/new error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
