import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiCreateFreeBoardRequest, ApiCreateFreeBoardResponse } from "@/types/api";

/** 2023/05/10 - 자유 게시글 - by 1-blue */
const handler: NextApiHandler<ApiCreateFreeBoardResponse> = async (req, res) => {
  await timer(3000);

  try {
    // 자유 게시글 생성
    if (req.method === "POST") {
      const body = req.body as ApiCreateFreeBoardRequest;

      console.log("body >> ", body);

      return res.status(200).json({ freeBoardId: 1 });
    }
  } catch (error) {
    console.error("/api/freeboard/new error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
