import type { BoardType, PageInfo } from ".";

/** 2023/05/11 - 댓글 타입 - by 1-blue */
export interface Comment {
  commentId: number;
  content: string;
  memberId: number;
  nickname: string;
  email: string;
  profileImageUrl: string;
  recommntCount: number;
  createdAt: Date;
  modifiedAt: Date;
  recomments?: Recomment[];
}

/** 2023/05/11 - 답글 타입 - by 1-blue */
export interface Recomment {
  recommentId: number;
  content: string;
  memberId: number;
  nickname: string;
  email: string;
  profileImageUrl: string;
  createdAt: Date;
  modifiedAt: Date;
}

// ============================== 댓글들 요청 ==============================
/** 2023/05/11 - 댓글들 요청 송신 타입 - by 1-blue */
export interface ApiFetchCommentsRequest {
  boardId: number;
  page: number;
  size: number;
}
/** 2023/05/11 - 댓글들 요청 수신 타입 - by 1-blue */
export interface ApiFetchCommentsResponse {
  data: Comment[];
  pageInfo: PageInfo;
}
/** 2023/05/11 - 댓글들 요청 핸들러 - by 1-blue */
export interface ApiFetchCommentsHandler {
  (type: BoardType, body: ApiFetchCommentsRequest): Promise<ApiFetchCommentsResponse>;
}

// ============================== 댓글 등록 ==============================
/** 2023/05/11 - 댓글 등록 송신 타입 - by 1-blue */
export interface ApiCreateCommentRequest {
  boardId: number;
  memberId: number;
  content: string;
}
/** 2023/05/11 - 댓글 등록 수신 타입 - by 1-blue */
export interface ApiCreateCommentResponse {
  commentId: number;
}
/** 2023/05/11 - 댓글 등록 핸들러 - by 1-blue */
export interface ApiCreateCommentHandler {
  (type: BoardType, body: ApiCreateCommentRequest): Promise<ApiCreateCommentResponse>;
}

// ============================== 댓글 수정 ==============================
/** 2023/05/11 - 댓글 수정 송신 타입 - by 1-blue */
export interface ApiUpdateCommentRequest {
  boardId: number;
  memberId: number;
  commentId: number;
  content: string;
}
/** 2023/05/11 - 댓글 수정 수신 타입 - by 1-blue */
export interface ApiUpdateCommentResponse {}
/** 2023/05/11 - 댓글 수정 핸들러 - by 1-blue */
export interface ApiUpdateCommentHandler {
  (type: BoardType, body: ApiUpdateCommentRequest): Promise<ApiUpdateCommentResponse>;
}

// ============================== 댓글 삭제 ==============================
/** 2023/05/11 - 댓글 삭제 송신 타입 - by 1-blue */
export interface ApiDeleteCommentRequest {
  boardId: number;
  commentId: number;
}
/** 2023/05/11 - 댓글 삭제 수신 타입 - by 1-blue */
export interface ApiDeleteCommentResponse {}
/** 2023/05/11 - 댓글 삭제 핸들러 - by 1-blue */
export interface ApiDeleteCommentHandler {
  (type: BoardType, body: ApiDeleteCommentRequest): Promise<ApiDeleteCommentResponse>;
}
