import { faker } from "@faker-js/faker";

import { timer } from "@/libs";

// type
import type { NextApiHandler } from "next";
import type { ApiFetchCommentsResponse, Comment, Recomment } from "@/types/api";

// 더미 답글
const getDummyRecomments: (n: number) => Recomment[] = (n) =>
  Array(n)
    .fill(null)
    .map((v, i) => ({
      recommentId: +faker.random.numeric(5),
      content: faker.lorem.lines(),
      createdAt: faker.date.recent(),
      modifiedAt: faker.date.recent(),
      email: faker.internet.email(),
      nickname: faker.name.fullName(),
      memberId: 1,
      profileImageUrl: faker.image.avatar(),
    }));

// 더미 댓글
const getDummyComments: (size: number) => Comment[] = (size) =>
  Array(size)
    .fill(null)
    .map((v, i) => ({
      commentId: +faker.random.numeric(5),
      content: faker.lorem.lines(),
      createdAt: faker.date.recent(),
      modifiedAt: faker.date.recent(),
      email: faker.internet.email(),
      nickname: faker.name.fullName(),
      memberId: 1,
      profileImageUrl: faker.image.avatar(),
      recommntCount: i,
      recomments: getDummyRecomments(i),
    }));

const TOTAL_PAGE = 8;

/** 2023/05/11 - 댓글들 요청 - by 1-blue */
const handler: NextApiHandler<ApiFetchCommentsResponse> = async (req, res) => {
  await timer(1500);

  try {
    if (req.method === "GET") {
      const feedbackBoardId = +(req.query.feedbackBoardId as string);
      const page = +(req.query.page as string);
      const size = +(req.query.size as string);

      return res.status(200).json({
        data: getDummyComments(size),
        pageInfo: {
          page,
          size,
          totalElements: TOTAL_PAGE * size,
          totalPages: TOTAL_PAGE,
        },
      });
    }
  } catch (error) {
    console.error("/api/jobboard/[jobBoardId]/comments error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
