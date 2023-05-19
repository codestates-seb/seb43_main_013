// import { faker } from "@faker-js/faker";

// import { timer } from "@/libs";

// // type
// import type { NextApiHandler } from "next";
// import type {
//   ApiDeleteFreeBoardResponse,
//   ApiFetchFreeBoardResponse,
//   ApiUpdateFreeBoardRequest,
//   ApiUpdateFreeBoardResponse,
//   FreeBoard,
// } from "@/types/api";

// // 임의로 자유 게시글 상세 내용
// const dummyFreeBoard: FreeBoard = {
//   freeBoardId: 1111,
//   title: faker.lorem.paragraph(),
//   content: Array(10)
//     .fill(null)
//     .map(() => "<p>" + faker.lorem.paragraphs() + "</p>" + "<br />")
//     .join(""),
//   commentCount: faker.datatype.number(), // 댓글수
//   likeCount: faker.datatype.number(), // 좋아요수
//   viewCount: faker.datatype.number(), // 조회수
//   tags: Array(8)
//     .fill(null)
//     .map((v, i) => ({
//       tagId: i,
//       tagName: faker.word.noun({ length: { min: 2, max: 5 } }),
//     })), // 태그
//   categoryName: faker.word.noun(), // 게시판 카테고리(먹방, 게임 스포츠 등)
//   createdAt: faker.date.recent(), // 작성 시간
//   modifiedAt: faker.date.recent(), // 수정 시간
//   memberId: 1,
//   email: "1-blue@naver.com", // 작성자 이메일
//   nickname: "1-blue", // 작성자 닉네임
//   profileImageUrl: faker.image.avatar(),
// };

// /** 2023/05/10 - 자유 게시글 생성/수정/삭제 요청 - by 1-blue */
// const handler: NextApiHandler<
//   ApiFetchFreeBoardResponse | ApiUpdateFreeBoardResponse | ApiDeleteFreeBoardResponse
// > = async (req, res) => {
//   await timer(3000);

//   try {
//     const freeBoardId = +(req.query.freeBoardId as string);

//     if (req.method === "GET") {
//       return res.status(201).json({
//         ...dummyFreeBoard,
//         freeBoardId,
//       });
//     }
//     if (req.method === "PATCH") {
//       const body = req.body as ApiUpdateFreeBoardRequest;

//       console.log("body >> ", body);

//       return res.status(200).json({});
//     }
//     if (req.method === "DELETE") {
//       return res.status(204).end();
//     }
//   } catch (error) {
//     console.error("/api/freeboard/[freeBoardId] error >> ", error);

//     return res.status(500).end();
//   }
// };

// export default handler;
