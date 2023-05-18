// ============================== 팔로우 요청 ==============================
/** 2023/05/18 - 팔로우 요청 송신 타입 - by 1-blue */
export interface ApiCreateFollowRequest {
  memberId: number;
}
/** 2023/05/18 - 팔로우 요청 수신 타입 - by 1-blue */
export interface ApiCreateFollowResponse {}
/** 2023/05/18 - 팔로우 요청 핸들러 - by 1-blue */
export interface ApiCreateFollowHandler {
  (body: ApiCreateFollowRequest): Promise<ApiCreateFollowResponse>;
}

// ============================== 언팔로우 요청 ==============================
/** 2023/05/18 - 언팔로우 요청 송신 타입 - by 1-blue */
export interface ApiDeleteFollowRequest {
  memberId: number;
}
/** 2023/05/18 - 언팔로우 요청 수신 타입 - by 1-blue */
export interface ApiDeleteFollowResponse {}
/** 2023/05/18 - 언팔로우 요청 핸들러 - by 1-blue */
export interface ApiDeleteFollowHandler {
  (body: ApiDeleteFollowRequest): Promise<ApiDeleteFollowResponse>;
}
