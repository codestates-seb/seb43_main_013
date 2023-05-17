// https://main-project.gitbook.io/main-project/undefined-1/undefined-2

import { PageInfo } from "..";
import type { Board, DetailTag, Tag } from "./";

/** 2023/05/10 - 홍보 게시판 타입 - by 1-blue */
export interface PromotionBoard extends Board {
  promotionBoardId: number;
  link: string; // 유튜브 링크
  channelName: string; // 유튜브 채널명
  subscriberCount: number; // 구독자수
  tags: DetailTag[];
  categoryName: string; // 카테고리
}

// ============================== C 홍보 게시판 생성 ==============================
/** 2023/05/10 - 홍보 게시판 생성 요청 송신 타입 - by 1-blue */
export interface ApiCreatePromotionBoardRequest {
  memberId: number;
  title: string;
  link: string;
  channelName: string;
  subscriberCount: number;
  content: string;
  tags: Tag[];
  categoryName: string;
}
/** 2023/05/10 - 홍보 게시판 생성 요청 수신 타입 - by 1-blue */
export interface ApiCreatePromotionBoardResponse {
  promotionBoardId: number;
}
/** 2023/05/10 - 홍보 게시판 생성 요청 핸들러 - by 1-blue */
export interface ApiCreatePromotionBoardHandler {
  (body: ApiCreatePromotionBoardRequest): Promise<ApiCreatePromotionBoardResponse>;
}

// ============================== R 홍보 게시판 상세 정보 ==============================
/** 2023/05/10 - 홍보 게시판 상세 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchPromotionBoardRequest {
  promotionBoardId: number;
}
/** 2023/05/10 - 홍보 게시판 상세 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchPromotionBoardResponse extends PromotionBoard {}
/** 2023/05/10 - 홍보 게시판 상세 정보 요청 핸들러 - by 1-blue */
export interface ApiFetchPromotionBoardHandler {
  (body: ApiFetchPromotionBoardRequest): Promise<ApiFetchPromotionBoardResponse>;
}

// ============================== U 홍보 게시판 수정 ==============================
/** 2023/05/10 - 홍보 게시판 수정 요청 송신 타입 - by 1-blue */
export interface ApiUpdatePromotionBoardRequest {
  promotionBoardId: number;
  title: string;
  link: string;
  channelName: string;
  subscriberCount: number;
  content: string;
  tags: Tag[];
  categoryName: string;
}
/** 2023/05/10 - 홍보 게시판 수정 요청 수신 타입 - by 1-blue */
export interface ApiUpdatePromotionBoardResponse {}
/** 2023/05/10 - 홍보 게시판 수정 요청 핸들러 - by 1-blue */
export interface ApiUpdatePromotionBoardHandler {
  (body: ApiUpdatePromotionBoardRequest): Promise<ApiUpdatePromotionBoardResponse>;
}

// ============================== D 홍보 게시판 삭제 ==============================
/** 2023/05/10 - 홍보 게시판 삭제 요청 송신 타입 - by 1-blue */
export interface ApiDeletePromotionBoardRequest {
  promotionBoardId: number;
}
/** 2023/05/10 - 홍보 게시판 삭제 요청 수신 타입 - by 1-blue */
export interface ApiDeletePromotionBoardResponse {}
/** 2023/05/10 - 홍보 게시판 삭제 요청 핸들러 - by 1-blue */
export interface ApiDeletePromotionBoardHandler {
  (body: ApiDeletePromotionBoardRequest): Promise<ApiDeletePromotionBoardResponse>;
}

// ============================== 홍보 게시판 게시글리스트 조회 ==============================
/** 2023/05/12- 홍보 게시판 게시글리스트 조회 요청 송신 타입 - by leekoby */
export interface ApiFetchPromotionBoardListRequest {
  page: number;
  size: number;
}
/** 2023/05/12 - 홍보 게시판 게시글리스트 조회 요청 수신 타입 - by leekoby */
export interface ApiFetchPromotionBoardListResponse {
  data: PromotionBoard[];
  pageInfo: PageInfo;
}
/** 2023/05/12 - 홍보 게시판 게시글리스트 조회 요청 핸들러 - by leekoby */
export interface ApiFetchPromotionBoardListHandler {
  (body: ApiFetchPromotionBoardListRequest): Promise<ApiFetchPromotionBoardListResponse>;
}
