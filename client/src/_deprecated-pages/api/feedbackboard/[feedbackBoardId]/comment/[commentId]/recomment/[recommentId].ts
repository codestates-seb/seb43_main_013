// import { timer } from "@/libs";

// // type
// import type { NextApiHandler } from "next";
// import type { ApiDeleteRecommentResponse, ApiUpdateRecommentResponse } from "@/types/api";

// /** 2023/05/13 - 답글 UD - by 1-blue */
// const handler: NextApiHandler<ApiUpdateRecommentResponse | ApiDeleteRecommentResponse> = async (req, res) => {
//   timer(1000);

//   try {
//     // 답글 수정
//     if (req.method === "PATCH") {
//       return res.status(200).json({ commentId: 1 });
//     }
//     // 답글 삭제
//     if (req.method === "DELETE") {
//       return res.status(204).json({});
//     }
//   } catch (error) {
//     console.error("/api/feedbackboard/[feedbackBoardId]/comment/[commentId]/recomment/[recommentId]  error >> ", error);

//     return res.status(500).end();
//   }
// };

// export default handler;
