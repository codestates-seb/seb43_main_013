// https://main-project.gitbook.io/main-project/undefined-1/undefined

import type { PageInfo } from "..";
import type { Board } from "./";

/** 2023/05/10 - 자유 게시판 타입 - by 1-blue */
export interface FreeBoard extends Board {
  freeBoardId: number;
  tag: string[]; // 태그
  categoryName: string; // 카테고리
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
}
/** 2023/05/10 - 자유 게시판 생성 요청 핸들러 - by 1-blue */
export interface ApiCreateFreeBoardHandler {
  (body: ApiCreateFreeBoardRequest): Promise<ApiCreateFreeBoardResponse>;
}

// ============================== R 자유 게시판 상세 정보 ==============================
/** 2023/05/10 - 자유 게시판 상세 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchFreeBoardRequest {
  freeBoardId: number;
}
/** 2023/05/10 - 자유 게시판 상세 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchFreeBoardResponse extends FreeBoard {}
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
export interface ApiUpdateFreeBoardResponse {}
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

// ============================== 자유 게시판들 조회 ==============================
/** 2023/05/11 - 자유 게시판들 조회 요청 송신 타입 - by leekoby */
export interface ApiFetchFreeBoardListRequest {
  page: number;
  size: number;
}
/** 2023/05/11 - 자유 게시판들 조회 요청 수신 타입 - by leekoby */
export interface ApiFetchFreeBoardListResponse {
  data: FreeBoard[];
  pageInfo: PageInfo;
}
/** 2023/05/11 - 자유 게시판 목록 조회 요청 핸들러 - by leekoby */
export interface ApiFetchFreeBoardListHandler {
  (body: ApiFetchFreeBoardListRequest): Promise<ApiFetchFreeBoardListResponse>;
}
