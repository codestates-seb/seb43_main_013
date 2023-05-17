// // type
// import type { NextApiHandler } from "next";

// /** 2023/05/04 - api 템플릿 - by 1-blue */
// const handler: NextApiHandler = async (req, res) => {
//   try {
//     if (req.method === "GET") {
//       return res.status(200).json({ message: "hello" });
//     }
//   } catch (error) {
//     console.error("/api/test error >> ", error);

//     return res.status(500).json({ message: "서버측 문제입니다.\n잠시후에 다시 시도해주세요!" });
//   }
// };

// export default handler;
