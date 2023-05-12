import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiDeleteCommentResponse, ApiUpdateCommentResponse } from "@/types/api";

/** 2023/05/11 - 댓글 UD - by 1-blue */
const handler: NextApiHandler<ApiUpdateCommentResponse | ApiDeleteCommentResponse> = async (req, res) => {
  timer(1000);

  try {
    // 댓글 수정
    if (req.method === "PATCH") {
      return res.status(200).json({ commentId: 1 });
    }
    // 댓글 삭제
    if (req.method === "DELETE") {
      return res.status(204).json({});
    }
  } catch (error) {
    console.error("/api/freeboard/[freeBoardId]/comment/[commentId]  error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
