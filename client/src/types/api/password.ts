// ============================== 비밀번호 확인 ==============================
/** 2023/05/18 - 비밀번호 확인 송신 타입 - by leekoby */
export interface ApiConfirmPasswordRequest {
  memberId: number;
  password: string;
}
/** 2023/05/18 - 비밀번호 확인 수신 타입 - by leekoby */
export interface ApiConfirmPasswordResponse {}
/** 2023/05/18 - 비밀번호 확인 핸들러 - by leekoby */
export interface ApiConfirmPasswordHandler {
  (body: ApiConfirmPasswordRequest): Promise<ApiConfirmPasswordResponse>;
}
