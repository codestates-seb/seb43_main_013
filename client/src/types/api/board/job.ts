// https://main-project.gitbook.io/main-project/undefined-1/undefined-3

import type { PageInfo } from "../";

/** 2023/05/10 - 구인구직 게시판 타입 - by 1-blue */
export interface JobBoard {
  jobBoardId: number;
  title: string; // 게시글 제목
  content: string; // 게시글 내용
  commentCount: number; // 댓글수
  likeCount: number; // 좋아요수
  viewCount: number; // 조회수
  jobCategoryName: string; // 카테고리
  createdAt: Date; // 작성 시간
  modifiedAt: Date; // 수정 시간
  memberId: number;
  email: string; // 작성자 이메일
  nickname: string; // 작성자 닉네임
}

// ============================== C 구인구직 게시판 생성 ==============================
/** 2023/05/10 - 구인구직 게시판 생성 요청 송신 타입 - by 1-blue */
export interface ApiCreateJobBoardRequest {
  memberId: number;
  title: string;
  content: string;
  jobCategoryName: string;
}
/** 2023/05/10 - 구인구직 게시판 생성 요청 수신 타입 - by 1-blue */
export interface ApiCreateJobBoardResponse {
  jobBoardId: number;
  title: string;
  content: string;
  jobCategoryName: string;
}

/** 2023/05/10 - 구인구직 게시판 생성 요청 핸들러 - by 1-blue */
export interface ApiCreateJobBoardHandler {
  (body: ApiCreateJobBoardRequest): Promise<ApiCreateJobBoardResponse>;
}
// ============================== R 구인구직 게시판 상세 정보 ==============================
/** 2023/05/10 - 구인구직 게시판 상세 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchJobBoardRequest {
  jobBoardId: number;
}
/** 2023/05/10 - 구인구직 게시판 상세 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchJobBoardResponse {
  data: JobBoard;
  pageInfo: PageInfo;
}
/** 2023/05/10 - 구인구직 게시판 상세 정보 요청 핸들러 - by 1-blue */
export interface ApiFetchJobBoardHandler {
  (body: ApiFetchJobBoardRequest): Promise<ApiFetchJobBoardResponse>;
}

// ============================== U 구인구직 게시판 수정 ==============================
/** 2023/05/10 - 구인구직 게시판 수정 요청 송신 타입 - by 1-blue */
export interface ApiUpdateJobBoardRequest {
  jobBoardId: number;
  title: string;
  content: string;
  jobCategoryName: string;
}
/** 2023/05/10 - 구인구직 게시판 수정 요청 수신 타입 - by 1-blue */
export interface ApiUpdateJobBoardResponse {
  jobBoardId: number;
  title: string;
  content: string;
  jobCategoryName: string;
}
/** 2023/05/10 - 구인구직 게시판 수정 요청 핸들러 - by 1-blue */
export interface ApiUpdateJobBoardHandler {
  (body: ApiUpdateJobBoardRequest): Promise<ApiUpdateJobBoardResponse>;
}

// ============================== D 구인구직 게시판 삭제 ==============================
/** 2023/05/10 - 구인구직 게시판 삭제 요청 송신 타입 - by 1-blue */
export interface ApiDeleteJobBoardRequest {
  jobBoardId: number;
}
/** 2023/05/10 - 구인구직 게시판 삭제 요청 수신 타입 - by 1-blue */
export interface ApiDeleteJobBoardResponse {}
/** 2023/05/10 - 구인구직 게시판 삭제 요청 핸들러 - by 1-blue */
export interface ApiDeleteJobBoardHandler {
  (body: ApiDeleteJobBoardRequest): Promise<ApiDeleteJobBoardResponse>;
}
