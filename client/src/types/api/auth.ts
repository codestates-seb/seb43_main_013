/** 2023/05/20 - 로그인한 유저 정보 - by 1-blue */
export interface Member {
  memberId: number;
  email: string;
  name: string;
  nickname: string;
  phone: string;
  oauth: boolean;
  introduction?: string;
  link?: string;
  profileImageUrl: string;
  createdAt: Date;
  modifiedAt: Date;
}

// ============================== 로그아웃 ==============================
/** 2023/05/18 - 로그아웃 송신 타입 - by 1-blue */
export interface ApiLogOutRequest {}
/** 2023/05/18 - 로그아웃 수신 타입 - by 1-blue */
export interface ApiLogOutResponse {}
/** 2023/05/18 - 로그아웃 핸들러 - by 1-blue */
export interface ApiLogOutHandler {
  (body: ApiLogOutRequest): Promise<ApiLogOutResponse>;
}

// ============================== 로그인 ==============================
/** 2023/05/20 - 로그인 송신 타입 - by 1-blue */
export interface ApiLogInRequest {
  username: string;
  password: string;
}
/** 2023/05/20 - 로그인 수신 타입 - by 1-blue */
export interface ApiLogInResponse extends Member {
  authorization: string;
  refreshtoken: string;
}
/** 2023/05/20 - 로그인 핸들러 - by 1-blue */
export interface ApiLogInHandler {
  (body: ApiLogInRequest): Promise<ApiLogInResponse>;
}

// ============================== 회원가입 ==============================
/** 2023/05/20 - 회원가입 송신 타입 - by 1-blue */
export interface ApiSignUpRequest {
  email: string;
  name: string;
  password: string;
  nickname: string;
  phone?: string;
  link?: string;
  introduction?: string;
  profileImageUrl?: string;
}
/** 2023/05/20 - 회원가입 수신 타입 - by 1-blue */
export interface ApiSignUpResponse {}
/** 2023/05/20 - 회원가입 핸들러 - by 1-blue */
export interface ApiSignUpHandler {
  (body: ApiSignUpRequest): Promise<ApiSignUpResponse>;
}
