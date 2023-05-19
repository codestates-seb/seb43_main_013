import type { BoardType } from ".";

// ============================== 게시글 좋아요 생성 ==============================
/** 2023/05/17 - 게시글 좋아요 생성 송신 타입 - by leekoby */
export interface ApiCreateLikeOfPostRequest {
  boardId: number;
}
/** 2023/05/17 - 게시글 좋아요 생성 수신 타입 - by leekoby */
export interface ApiCreateLikeOfPostResponse {}
/** 2023/05/17 - 게시글 좋아요 생성 핸들러 - by leekoby */
export interface ApiCreateLikeOfPostHandler {
  (type: BoardType, body: ApiCreateLikeOfPostRequest): Promise<ApiCreateLikeOfPostResponse>;
}

// ============================== 게시글 좋아요 제거 ==============================
/** 2023/05/17 - 게시글 좋아요 제거 송신 타입 - by leekoby */
export interface ApiDeleteLikeOfPostRequest {
  boardId: number;
}
/** 2023/05/17 - 게시글 좋아요 제거 수신 타입 - by leekoby */
export interface ApiDeleteLikeOfPostResponse {}
/** 2023/05/17 - 게시글 좋아요 제거 핸들러 - by leekoby */
export interface ApiDeleteLikeOfPostHandler {
  (type: BoardType, body: ApiDeleteLikeOfPostRequest): Promise<ApiDeleteLikeOfPostResponse>;
}
