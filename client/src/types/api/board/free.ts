// https://main-project.gitbook.io/main-project/undefined-1/undefined

import type { PageInfo } from "../";

/** 2023/05/10 - 자유 게시판 타입 - by 1-blue */
export interface FreeBoard {
  freeBoardId: number;
  title: string; // 게시글 제목
  content: string; // 게시글 내용
  commentCount: number; // 댓글수
  likeCount: number; // 좋아요수
  viewCount: number; // 조회수
  tag: string[]; // 태그
  categoryName: string; // 카테고리
  createdAt: Date; // 작성 시간
  modifiedAt: Date; // 수정 시간
  memberId: number;
  email: string; // 작성자 이메일
  nickname: string; // 작성자 닉네임
}

// ============================== C 자유 게시판 생성 ==============================
/** 2023/05/10 - 자유 게시판 생성 요청 송신 타입 - by 1-blue */
export interface ApiCreateFreeBoardRequest {
  memberId: number;
  title: string;
  content: string;
  tag: string[];
  categoryName: string;
}
/** 2023/05/10 - 자유 게시판 생성 요청 수신 타입 - by 1-blue */
export interface ApiCreateFreeBoardResponse {
  freeBoardId: number;
  title: string;
  content: string;
  tag: string[];
  categoryName: string;
}
/** 2023/05/10 - 자유 게시판 삭제 요청 핸들러 - by 1-blue */
export interface ApiCreateFreeBoardHandler {
  (body: ApiCreateFreeBoardRequest): Promise<ApiCreateFreeBoardResponse>;
}
// ============================== R 자유 게시판 상세 정보 ==============================
/** 2023/05/10 - 자유 게시판 상세 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchFreeBoardRequest {
  freeBoardId: number;
}
/** 2023/05/10 - 자유 게시판 상세 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchFreeBoardResponse {
  data: FreeBoard;
  pageInfo: PageInfo;
}
/** 2023/05/10 - 자유 게시판 상세 정보 요청 핸들러 - by 1-blue */
export interface ApiFetchFreeBoardHandler {
  (body: ApiFetchFreeBoardRequest): Promise<ApiFetchFreeBoardResponse>;
}
// ============================== U 자유 게시판 수정 ==============================
/** 2023/05/10 - 자유 게시판 수정 요청 송신 타입 - by 1-blue */
export interface ApiUpdateFreeBoardRequest {
  freeBoardId: number;
  title: string;
  content: string;
  tag: string[];
  categoryName: string;
}
/** 2023/05/10 - 자유 게시판 수정 요청 수신 타입 - by 1-blue */
export interface ApiUpdateFreeBoardResponse {
  freeBoardId: number;
  title: string;
  content: string;
  tag: string[];
  categoryName: string;
}
/** 2023/05/10 - 자유 게시판 수정 요청 핸들러 - by 1-blue */
export interface ApiUpdateFreeBoardHandler {
  (body: ApiUpdateFreeBoardRequest): Promise<ApiUpdateFreeBoardResponse>;
}
// ============================== D 자유 게시판 삭제 ==============================
/** 2023/05/10 - 자유 게시판 삭제 요청 송신 타입 - by 1-blue */
export interface ApiDeleteFreeBoardRequest {
  freeBoardId: number;
}
/** 2023/05/10 - 자유 게시판 삭제 요청 수신 타입 - by 1-blue */
export interface ApiDeleteFreeBoardResponse {}
/** 2023/05/10 - 자유 게시판 삭제 요청 핸들러 - by 1-blue */
export interface ApiDeleteFreeBoardHandler {
  (body: ApiDeleteFreeBoardRequest): Promise<ApiDeleteFreeBoardResponse>;
}
