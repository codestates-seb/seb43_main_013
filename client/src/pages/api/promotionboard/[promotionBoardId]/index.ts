import { faker } from "@faker-js/faker";

import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type {
  ApiDeletePromotionBoardResponse,
  ApiFetchPromotionBoardResponse,
  ApiUpdatePromotionBoardRequest,
  ApiUpdatePromotionBoardResponse,
  PromotionBoard,
} from "@/types/api";

// 임의로 홍보 게시글 상세 내용
const dummyPromotionBoard: PromotionBoard = {
  promotionBoardId: 1111,
  title: faker.lorem.paragraph(),
  link: "https://www.youtube.com/watch?v=3gMAEZCOFtE",
  channelName: "대충 사는 채널",
  subscriberCount: 2000,
  content: faker.lorem.paragraphs(),
  commentCount: faker.datatype.number(), // 댓글수
  likeCount: faker.datatype.number(), // 좋아요수
  viewCount: faker.datatype.number(), // 조회수
  tags: Array(8)
    .fill(null)
    .map((v, i) => ({
      tagId: i,
      tagName: faker.word.noun({ length: { min: 2, max: 5 } }),
    })), // 태그
  categoryName: faker.word.noun(), // 게시판 카테고리(먹방, 게임 스포츠 등)
  createdAt: faker.date.recent(), // 작성 시간
  modifiedAt: faker.date.recent(), // 수정 시간
  memberId: 1,
  email: "1-blue@naver.com", // 작성자 이메일
  nickname: "1-blue", // 작성자 닉네임
  profileImageUrl: faker.image.avatar(),
};

/** 2023/05/10 - 홍보 게시글 생성/수정/삭제 요청 - by 1-blue */
const handler: NextApiHandler<
  ApiFetchPromotionBoardResponse | ApiUpdatePromotionBoardResponse | ApiDeletePromotionBoardResponse
> = async (req, res) => {
  await timer(3000);

  try {
    const promotionBoardId = +(req.query.promotionBoardId as string);

    if (req.method === "GET") {
      return res.status(201).json({
        ...dummyPromotionBoard,
        promotionBoardId,
      });
    }
    if (req.method === "PATCH") {
      const body = req.body as ApiUpdatePromotionBoardRequest;

      console.log("body >> ", body);

      return res.status(200).json({});
    }
    if (req.method === "DELETE") {
      return res.status(204).end();
    }
  } catch (error) {
    console.error("/api/promotionboard/[promotionBoardId] error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
