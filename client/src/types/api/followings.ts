import type { PageInfo } from ".";

// ============================== 특정 유저의 팔로잉들 요청 ==============================
/** 2023/05/18 - 특정 유저의 팔로잉들 요청 송신 타입 - by 1-blue */
export interface ApiFetchFollowingsRequest {
  memberId: number;
  page: number;
  size: number;
}
/** 2023/05/18 - 특정 유저의 팔로잉들 요청 수신 타입 - by 1-blue */
export interface ApiFetchFollowingsResponse {
  data: {
    memberId: number;
    nickname: string;
    profileImageUrl: string;
    followed: boolean;
  }[];
  pageInfo: PageInfo;
}
/** 2023/05/18 - 특정 유저의 팔로잉들 요청 핸들러 - by 1-blue */
export interface ApiFetchFollowingsHandler {
  (body: ApiFetchFollowingsRequest): Promise<ApiFetchFollowingsResponse>;
}
