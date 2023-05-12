import { faker } from "@faker-js/faker";

import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type {
  ApiDeleteJobBoardResponse,
  ApiFetchJobBoardResponse,
  ApiUpdateJobBoardRequest,
  ApiUpdateJobBoardResponse,
  JobBoard,
} from "@/types/api";

// 임의로 구인구직 게시글 상세 내용
const dummyJobBoard: JobBoard = {
  jobBoardId: 1111,
  title: faker.lorem.paragraph(),
  content: faker.lorem.paragraphs(),
  commentCount: faker.datatype.number(), // 댓글수
  likeCount: faker.datatype.number(), // 좋아요수
  viewCount: faker.datatype.number(), // 조회수
  jobCategoryName: faker.word.noun(), // 카테고리
  createdAt: faker.date.recent(), // 작성 시간
  modifiedAt: faker.date.recent(), // 수정 시간
  memberId: 1,
  email: "1-blue@naver.com", // 작성자 이메일
  nickname: "1-blue", // 작성자 닉네임
  profileImageUrl: faker.image.avatar(),
};

/** 2023/05/10 - 구인구직 게시글 생성/수정/삭제 요청 - by 1-blue */
const handler: NextApiHandler<
  ApiFetchJobBoardResponse | ApiUpdateJobBoardResponse | ApiDeleteJobBoardResponse
> = async (req, res) => {
  await timer(3000);

  try {
    const jobBoardId = +(req.query.jobBoardId as string);

    if (req.method === "GET") {
      return res.status(201).json({
        ...dummyJobBoard,
        jobBoardId,
      });
    }
    if (req.method === "PATCH") {
      const body = req.body as ApiUpdateJobBoardRequest;

      console.log("body >> ", body);

      return res.status(200).json({});
    }
    if (req.method === "DELETE") {
      return res.status(204).end();
    }
  } catch (error) {
    console.error("/api/jobboard/[jobBoardId] error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
