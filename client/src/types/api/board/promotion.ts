// https://main-project.gitbook.io/main-project/undefined-1/undefined-2

import type { PageInfo } from "..";

/** 2023/05/10 - 홍보 게시판 타입 - by 1-blue */
export interface PromotionBoard {
  promotionBoardId: number;
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

  // FIXME:
  link: string; // 유튜브 링크
  channelName: string; // 유튜브 채널명
  subscriberCount: number; // 구독자수
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
  tag: string[];
  categoryName: string;
}
/** 2023/05/10 - 홍보 게시판 생성 요청 수신 타입 - by 1-blue */
export interface ApiCreatePromotionBoardResponse {
  promotionBoardId: number;
  title: string;
  link: string;
  channelName: string;
  subscriberCount: number;
  content: string;
  tag: string[];
  categoryName: string;
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
export interface ApiFetchPromotionBoardResponse {
  data: PromotionBoard;
  pageInfo: PageInfo;
}
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
  tag: string[];
  categoryName: string;
}
/** 2023/05/10 - 홍보 게시판 수정 요청 수신 타입 - by 1-blue */
export interface ApiUpdatePromotionBoardResponse {
  promotionBoardId: number;
  title: string;
  link: string;
  channelName: string;
  subscriberCount: number;
  content: string;
  tag: string[];
  categoryName: string;
}
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
