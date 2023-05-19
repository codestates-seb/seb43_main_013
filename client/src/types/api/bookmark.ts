import type { BoardType } from ".";

// ============================== 북마크 생성 ==============================
/** 2023/05/17 - 북마크 생성 송신 타입 - by leekoby */
export interface ApiCreateBookmarkRequest {
  boardId: number;
}
/** 2023/05/17 - 북마크 생성 수신 타입 - by leekoby */
export interface ApiCreateBookmarkResponse {}
/** 2023/05/17 - 북마크 생성 핸들러 - by leekoby */
export interface ApiCreateBookmarkHandler {
  (type: BoardType, body: ApiCreateBookmarkRequest): Promise<ApiCreateBookmarkResponse>;
}

// ============================== 북마크 제거 ==============================
/** 2023/05/17 - 북마크 제거 송신 타입 - by leekoby */
export interface ApiDeleteBookmarkRequest {
  boardId: number;
}
/** 2023/05/17 - 북마크 제거 수신 타입 - by leekoby */
export interface ApiDeleteBookmarkResponse {}
/** 2023/05/17 - 북마크 제거 핸들러 - by leekoby */
export interface ApiDeleteBookmarkHandler {
  (type: BoardType, body: ApiDeleteBookmarkRequest): Promise<ApiDeleteBookmarkResponse>;
}
