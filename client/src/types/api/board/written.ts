// https://main-project.gitbook.io/main-project/undefined-1/undefined-3

import type { BoardOfProfile } from "./";
import type { PageInfo } from "..";

// ============================== 내가 작성한 게시판 리스트 정보 ==============================
/** 2023/05/17 - 내가 작성한 게시판 리스트 정보 요청 송신 타입 - by 1-blue */
export interface ApiFetchWrittenBoardListRequest {
  memberId: number;
  page: number;
  size: number;
}
/** 2023/05/17 - 내가 작성한 게시판 리스트 정보 요청 수신 타입 - by 1-blue */
export interface ApiFetchWrittenBoardListResponse {
  data: BoardOfProfile[];
  pageInfo: PageInfo;
}
/** 2023/05/17 - 내가 작성한 게시판 리스트 정보 요청 핸들러 - by 1-blue */
export interface ApiFetchWrittenBoardListHandler {
  (body: ApiFetchWrittenBoardListRequest): Promise<ApiFetchWrittenBoardListResponse>;
}
