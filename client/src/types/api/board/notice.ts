//https://newmainproject.gitbook.io/main-project/undefined-4

import { PageInfo } from "..";
import type { Board } from "./";

/** 2023/05/20 - 공지사항 타입 - by leekoby */
export interface NoticeBoard extends Board {
  noticeId: number;
}

// ============================== C 공지사항 생성 ==============================
/** 2023/05/20 - 공지사항 생성 요청 송신 타입 - by leekoby */
export interface ApiCreateNoticeBoardRequest {
  memberId: number;
  title: string;
  content: string;
}
/** 2023/05/20 - 공지사항 생성 요청 수신 타입 - by leekoby */
export interface ApiCreateNoticeBoardResponse {
  noticeId: number;
}
/** 2023/05/20 - 공지사항 생성 요청 핸들러 - by leekoby */
export interface ApiCreateNoticeBoardHandler {
  (body: ApiCreateNoticeBoardRequest): Promise<ApiCreateNoticeBoardResponse>;
}

// ============================== R 공지사항 상세 정보 ==============================
/** 2023/05/20 - 공지사항 상세 정보 요청 송신 타입 - by leekoby */
export interface ApiFetchNoticeBoardRequest {
  noticeId: number;
}
/** 2023/05/20 - 공지사항 상세 정보 요청 수신 타입 - by leekoby */
export interface ApiFetchNoticeBoardResponse extends NoticeBoard {}
/** 2023/05/20 - 공지사항 상세 정보 요청 핸들러 - by leekoby */
export interface ApiFetchNoticeBoardHandler {
  (body: ApiFetchNoticeBoardRequest): Promise<ApiFetchNoticeBoardResponse>;
}

// ============================== U 공지사항 수정 ==============================
/** 2023/05/20 - 공지사항 수정 요청 송신 타입 - by leekoby */
export interface ApiUpdateNoticeBoardRequest {
  noticeId: number;
  title: string;
  content: string;
}
/** 2023/05/20 - 공지사항 수정 요청 수신 타입 - by leekoby */
export interface ApiUpdateNoticeBoardResponse {}
/** 2023/05/20 - 공지사항 수정 요청 핸들러 - by leekoby */
export interface ApiUpdateNoticeBoardHandler {
  (body: ApiUpdateNoticeBoardRequest): Promise<ApiUpdateNoticeBoardResponse>;
}

// ============================== D 공지사항 삭제 ==============================
/** 2023/05/20 - 공지사항 삭제 요청 송신 타입 - by leekoby */
export interface ApiDeleteNoticeBoardRequest {
  noticeId: number;
}
/** 2023/05/20 - 공지사항 삭제 요청 수신 타입 - by leekoby */
export interface ApiDeleteNoticeBoardResponse {}
/** 2023/05/20 - 공지사항 삭제 요청 핸들러 - by leekoby */
export interface ApiDeleteNoticeBoardHandler {
  (body: ApiDeleteNoticeBoardRequest): Promise<ApiDeleteNoticeBoardResponse>;
}

// ============================== 공지사항 게시글리스트 조회 ==============================
/** 2023/05/20 - 공지사항 게시글리스트 조회 요청 송신 타입 - by leekoby */
export interface ApiFetchNoticeBoardListRequest {
  sorted: string;
  page: number;
  size: number;
}
/** 2023/05/20 - 공지사항 게시글리스트 조회 요청 수신 타입 - by leekoby */
export interface ApiFetchNoticeBoardListResponse {
  data: NoticeBoard[];
  pageInfo: PageInfo;
}
/** 2023/05/20 - 공지사항 게시글리스트 조회 요청 핸들러 - by leekoby */
export interface ApiFetchNoticeBoardListHandler {
  (body: ApiFetchNoticeBoardListRequest): Promise<ApiFetchNoticeBoardListResponse>;
}
