import { faker } from "@faker-js/faker";

// type
import type { NextApiHandler } from "next";
import type { ApiFetchFreeBoardListResponse, FreeBoard } from "@/types/api";
import { timer } from "@/libs";

// 임의로 자유 게시글 리스트 가져오는 함수
const getDummyFreeBoardList: (n: number) => FreeBoard[] = (n) =>
  Array(n)
    .fill(null)
    .map(() => ({
      freeBoardId: +faker.random.numeric(8),
      title: faker.lorem.paragraph(),
      content: faker.lorem.paragraphs(),
      commentCount: faker.datatype.number(), // 댓글수
      likeCount: faker.datatype.number(), // 좋아요수
      viewCount: faker.datatype.number(), // 조회수
      tag: Array(8)
        .fill(null)
        .map(() => faker.word.noun({ length: { min: 2, max: 5 } })), // 태그
      categoryName: faker.word.noun(), // 카테고리
      createdAt: new Date(faker.date.recent()), // 작성 시간
      modifiedAt: new Date(faker.date.recent()), // 수정 시간
      memberId: 1,
      email: "1-blue@naver.com", // 작성자 이메일
      nickname: "1-blue", // 작성자 닉네임
      profileImageUrl: faker.image.avatar(),
    }));

const TOTAL_PAGES = 8;

/** 2023/05/11 - 자유 게시글 리스트 엔드포인트 - by leekoby */
const handler: NextApiHandler<ApiFetchFreeBoardListResponse> = async (req, res) => {
  await timer(1000);

  try {
    if (req.method === "GET") {
      const page = +(req.query.page as string);
      const size = +(req.query.size as string);

      return res.status(200).json({
        data: getDummyFreeBoardList(size),
        pageInfo: {
          page,
          size,
          totalElements: TOTAL_PAGES * size,
          totalPages: TOTAL_PAGES,
        },
      });
    }
  } catch (error) {
    console.error("/api/freeboards error >> ", error);

    return res.status(500).end();
  }
};

export default handler;
