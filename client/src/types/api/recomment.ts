import type { BoardType } from ".";

// ============================== 답글 등록 ==============================
/** 2023/05/13 - 답글 등록 송신 타입 - by 1-blue */
export interface ApiCreateRecommentRequest {
  boardId: number;
  commentId: number;
  memberId: number;
  content: string;
}
/** 2023/05/13 - 답글 등록 수신 타입 - by 1-blue */
export interface ApiCreateRecommentResponse {
  recommentId: number;
}
/** 2023/05/13 - 답글 등록 핸들러 - by 1-blue */
export interface ApiCreateRecommentHandler {
  (type: BoardType, body: ApiCreateRecommentRequest): Promise<ApiCreateRecommentResponse>;
}

// ============================== 답글 수정 ==============================
/** 2023/05/13 - 답글 수정 송신 타입 - by 1-blue */
export interface ApiUpdateRecommentRequest {
  boardId: number;
  commentId: number;
  recommentId: number;
  memberId: number;
  content: string;
}
/** 2023/05/13 - 답글 수정 수신 타입 - by 1-blue */
export interface ApiUpdateRecommentResponse {}
/** 2023/05/13 - 답글 수정 핸들러 - by 1-blue */
export interface ApiUpdateRecommentHandler {
  (type: BoardType, body: ApiUpdateRecommentRequest): Promise<ApiUpdateRecommentResponse>;
}

// ============================== 답글 삭제 ==============================
/** 2023/05/13 - 답글 삭제 송신 타입 - by 1-blue */
export interface ApiDeleteRecommentRequest {
  boardId: number;
  commentId: number;
  recommentId: number;
}
/** 2023/05/13 - 답글 삭제 수신 타입 - by 1-blue */
export interface ApiDeleteRecommentResponse {}
/** 2023/05/13 - 답글 삭제 핸들러 - by 1-blue */
export interface ApiDeleteRecommentHandler {
  (type: BoardType, body: ApiDeleteRecommentRequest): Promise<ApiDeleteRecommentResponse>;
}
