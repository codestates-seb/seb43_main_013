import {
  ApiFetchFeedbackBoardHandler,
  ApiFetchFreeBoardHandler,
  ApiFetchJobBoardHandler,
  ApiFetchMemberHandler,
  ApiFetchPromotionBoardHandler,
  ApiFetchSearchHandler,
} from "@/types/api";

const BASE_URL = process.env.NEXT_PUBLIC_BASE_URL + "/api";

/** 2023/05/25 - ssr 피드백 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiSSRFetchFeedbackBoard: ApiFetchFeedbackBoardHandler = async ({ feedbackBoardId }) =>
  fetch(`${BASE_URL}/feedbackboard/${feedbackBoardId}`).then((res) => res.json());

/** 2023/05/25 - ssr 자유 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiSSRFetchFreeBoard: ApiFetchFreeBoardHandler = async ({ freeBoardId }) =>
  fetch(`${BASE_URL}/freeboard/${freeBoardId}`).then((res) => res.json());

/** 2023/05/25 - ssr 구인구직 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiSSRFetchJobBoard: ApiFetchJobBoardHandler = async ({ jobBoardId }) =>
  fetch(`${BASE_URL}/jobboard/${jobBoardId}`).then((res) => res.json());

/** 2023/05/25 - ssr 홍보 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiSSRFetchPromotionBoard: ApiFetchPromotionBoardHandler = async ({ promotionBoardId }) =>
  fetch(`${BASE_URL}/promotionboard/${promotionBoardId}`).then((res) => res.json());

/** 2023/05/25 - ssr 홍보 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiSSRFetchSearchBoard: ApiFetchSearchHandler = async ({ keyword, page, size }) =>
  fetch(`${BASE_URL}/search?keyword=${keyword}&page=${page}&size=${size}`, {
    method: "GET",
    next: { revalidate: 60 },
  }).then((res) => res.json());

/** 2023/05/25 - 특정 멤버 조회 요청 - by 1-blue */
export const apiSSRFetchMember: ApiFetchMemberHandler = async ({ memberId }) =>
  fetch(`${BASE_URL}/member/${memberId}`).then((res) => res.json());
