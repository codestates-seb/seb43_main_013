// https://main-project.gitbook.io/main-project/undefined-1/undefined-1

import { PageInfo } from "..";
import type { Board } from "./";

/** 2023/05/10 - 피드백 게시판 타입 - by 1-blue */
export interface FeedbackBoard extends Board {
  feedbackBoardId: number;
  link: string;
  tag: string[]; // 태그
  categoryName: string; // 카테고리
  feedbackCateogoryName: string; // 피드백 게시판 카테고리 (영상, 채널, 썸네일)
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
}
/** 2023/05/10 - 피드백 게시판 생성 요청 핸들러 - by 1-blue */
export interface ApiCreateFeedbackBoardHandler {
  (body: ApiCreateFeedbackBoardRequest): Promise<ApiCreateFeedbackBoardResponse>;
}

// ============================== R 피드백 게시판 상세 정보 ==============================
/** 2023/05/10 - 피드백 게시판 상세 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchFeedbackBoardRequest {
  feedbackBoardId: number;
}
/** 2023/05/10 - 피드백 게시판 상세 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchFeedbackBoardResponse extends FeedbackBoard {}
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
export interface ApiUpdateFeedbackBoardResponse {}
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

// ============================== 피드백 게시판 게시글리스트 조회 ==============================
/** 2023/05/12- 피드백 게시판 게시글리스트 조회 요청 송신 타입 - by leekoby */
export interface ApiFetchFeedbackBoardListRequest {
  sorted: string;
  page: number;
  size: number;
}
/** 2023/05/12 - 피드백 게시판 게시글리스트 조회 요청 수신 타입 - by leekoby */
export interface ApiFetchFeedbackBoardListResponse {
  data: FeedbackBoard[];
  pageInfo: PageInfo;
}
/** 2023/05/12 - 피드백 게시판 게시글리스트 조회 요청 핸들러 - by leekoby */
export interface ApiFetchFeedbackBoardListHandler {
  (body: ApiFetchFeedbackBoardListRequest): Promise<ApiFetchFeedbackBoardListResponse>;
}
