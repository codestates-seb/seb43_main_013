// https://main-project.gitbook.io/main-project/undefined-1/undefined-1

import type { PageInfo } from "../";

/** 2023/05/10 - 피드백 게시판 타입 - by 1-blue */
export interface FeedbackBoard {
  feedbackBoardId: number;
  title: string; // 게시글 제목
  link: string;
  content: string; // 게시글 내용
  commentCount: number; // 댓글수
  likeCount: number; // 좋아요수
  viewCount: number; // 조회수
  tag: string[]; // 태그
  categoryName: string; // 카테고리
  feedbackCateogoryName: string; // 피드백 게시판 카테고리 (영상, 채널, 썸네일)
  createdAt: Date; // 작성 시간
  modifiedAt: Date; // 수정 시간
  memberId: number;
  email: string; // 작성자 이메일
  nickname: string; // 작성자 닉네임
}

// ============================== C 피드백 게시판 생성 ==============================
/** 2023/05/10 - 피드백 게시판 생성 요청 송신 타입 - by 1-blue */
export interface ApiCreateFeedbackBoardRequest {
  memberId: number;
  title: string;
  link: string;
  content: string;
  tag: string[];
  categoryName: string;
  feedbackCateogoryName: string;
}
/** 2023/05/10 - 피드백 게시판 생성 요청 수신 타입 - by 1-blue */
export interface ApiCreateFeedbackBoardResponse {
  feedbackBoardId: number;
  title: string;
  link: string;
  content: string;
  tag: string[];
  categoryName: string;
  feedbackCateogoryName: string;
}
/** 2023/05/10 - 피드백 게시판 삭제 요청 핸들러 - by 1-blue */
export interface ApiCreateFeedbackBoardHandler {
  (body: ApiCreateFeedbackBoardRequest): Promise<ApiCreateFeedbackBoardResponse>;
}
// ============================== R 피드백 게시판 상세 정보 ==============================
/** 2023/05/10 - 피드백 게시판 상세 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchFeedbackBoardRequest {
  feedbackBoardId: number;
}
/** 2023/05/10 - 피드백 게시판 상세 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchFeedbackBoardResponse {
  data: FeedbackBoard;
  pageInfo: PageInfo;
}
/** 2023/05/10 - 피드백 게시판 상세 정보 요청 핸들러 - by 1-blue */
export interface ApiFetchFeedbackBoardHandler {
  (body: ApiFetchFeedbackBoardRequest): Promise<ApiFetchFeedbackBoardResponse>;
}
// ============================== U 피드백 게시판 수정 ==============================
/** 2023/05/10 - 피드백 게시판 수정 요청 송신 타입 - by 1-blue */
export interface ApiUpdateFeedbackBoardRequest {
  feedbackBoardId: number;
  title: string;
  link: string;
  content: string;
  tag: string[];
  categoryName: string;
  feedbackCateogoryName: string;
}
/** 2023/05/10 - 피드백 게시판 수정 요청 수신 타입 - by 1-blue */
export interface ApiUpdateFeedbackBoardResponse {
  feedbackBoardId: number;
  title: string;
  link: string;
  content: string;
  tag: string[];
  categoryName: string;
  feedbackCateogoryName: string;
}
/** 2023/05/10 - 피드백 게시판 수정 요청 핸들러 - by 1-blue */
export interface ApiUpdateFeedbackBoardHandler {
  (body: ApiUpdateFeedbackBoardRequest): Promise<ApiUpdateFeedbackBoardResponse>;
}
// ============================== D 피드백 게시판 삭제 ==============================
/** 2023/05/10 - 피드백 게시판 삭제 요청 송신 타입 - by 1-blue */
export interface ApiDeleteFeedbackBoardRequest {
  feedbackBoardId: number;
}
/** 2023/05/10 - 피드백 게시판 삭제 요청 수신 타입 - by 1-blue */
export interface ApiDeleteFeedbackBoardResponse {}
/** 2023/05/10 - 피드백 게시판 삭제 요청 핸들러 - by 1-blue */
export interface ApiDeleteFeedbackBoardHandler {
  (body: ApiDeleteFeedbackBoardRequest): Promise<ApiDeleteFeedbackBoardResponse>;
}
