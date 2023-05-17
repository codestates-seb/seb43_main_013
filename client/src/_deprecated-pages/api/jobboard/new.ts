// import { timer } from "@/libs";

// // type
// import type { NextApiHandler } from "next";
// import type { ApiCreateJobBoardRequest, ApiCreateJobBoardResponse } from "@/types/api";

// /** 2023/05/10 - 구인구직 게시글 - by 1-blue */
// const handler: NextApiHandler<ApiCreateJobBoardResponse> = async (req, res) => {
//   await timer(3000);

//   try {
//     // 구인구직 게시글 생성
//     if (req.method === "POST") {
//       const body = req.body as ApiCreateJobBoardRequest;

//       return res.status(200).json({ ...body, jobBoardId: 1 });
//     }
//   } catch (error) {
//     console.error("/api/jobboard/new error >> ", error);

//     return res.status(500).end();
//   }
// };

// export default handler;
