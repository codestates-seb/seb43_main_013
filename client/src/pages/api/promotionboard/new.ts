import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiCreatePromotionBoardRequest, ApiCreatePromotionBoardResponse } from "@/types/api";

/** 2023/05/10 - 홍보 게시글 - by 1-blue */
const handler: NextApiHandler<ApiCreatePromotionBoardResponse> = async (req, res) => {
  await timer(3000);

  try {
    // 홍보 게시글 생성
    if (req.method === "POST") {
      const body = req.body as ApiCreatePromotionBoardRequest;

      return res.status(200).json({ ...body, promotionBoardId: 1 });
    }
  } catch (error) {
    console.error("/api/freeboard/new error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
