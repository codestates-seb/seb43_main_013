import type { PageInfo } from ".";

type BoardType = "FREEBOARD" | "FEEDBACKBOARD" | "JOBBOARD" | "PROMOTIONBOARD";
export interface SearchBoard {
  boardType: BoardType;
  id: number;
  title: string;
  content: string;
  commentCount: number;
  likeCount: number;
  viewCount: number;
  categoryName: string;
  createdAt: Date;
  modifiedAt: Date;
  memberId: number;
  profileImageUrl: string;
  name?: string | null;
  nickname?: string | null;
  email?: string | null;
}
export interface SearchMember {
  boardType: "MEMBER";
  id: number;
  memberId: number;
  email: string;
  name: string;
  nickname: string;
  profileImageUrl: string;
  createdAt: Date;
  modifiedAt: Date;
  content?: string | null;
  categoryName?: string | null;
  title?: string | null;
  commentCount?: number | null;
  likeCount?: number | null;
  viewCount?: number | null;
  introduction?: string | null;
  followers?: number | null;
  followings?: number | null;
}

// ============================== 검색 요청 ==============================
/** 2023/05/22 - 검색 요청 송신 타입 - by 1-blue */
export interface ApiFetchSearchRequest {
  keyword: string;
  page: number;
  size: number;
}
/** 2023/05/22 - 검색 요청 수신 타입 - by 1-blue */
export interface ApiFetchSearchResponse {
  data: (SearchBoard | SearchMember)[];
  pageInfo: PageInfo;
}
/** 2023/05/22 - 검색 요청 핸들러 - by 1-blue */
export interface ApiFetchSearchHandler {
  (body: ApiFetchSearchRequest): Promise<ApiFetchSearchResponse>;
}
