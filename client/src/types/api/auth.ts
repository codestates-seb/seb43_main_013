// ============================== 로그아웃 ==============================
/** 2023/05/18 - 로그아웃 송신 타입 - by 1-blue */
export interface ApiLogOutRequest {}
/** 2023/05/18 - 로그아웃 수신 타입 - by 1-blue */
export interface ApiLogOutResponse {}
/** 2023/05/18 - 로그아웃 핸들러 - by 1-blue */
export interface ApiLogOutHandler {
  (body: ApiLogOutRequest): Promise<ApiLogOutResponse>;
}

// ============================== 회원탈퇴 ==============================
/** 2023/05/24 - 회원탈퇴 송신 타입 - by 1-blue */
export interface ApiSignOutRequest {
  memberId: number;
}
/** 2023/05/24 - 회원탈퇴 수신 타입 - by 1-blue */
export interface ApiSignOutResponse {}
/** 2023/05/24 - 회원탈퇴 핸들러 - by 1-blue */
export interface ApiSignOutHandler {
  (body: ApiSignOutRequest): Promise<ApiSignOutResponse>;
}
