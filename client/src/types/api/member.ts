/** 2023/05/17 - 멤버 타입 - by 1-blue */
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
  followed: boolean;
  myPage: boolean;
  createdAt: Date;
  modifiedAt: Date;
  followerCount: number;
  followingCount: number;
}

// ============================== 특정 멤버 조회 ==============================
/** 2023/05/17 - 특정 멤버 조회 송신 타입 - by leekoby */
export interface ApiFetchMemberRequest {
  memberId: number;
}
/** 2023/05/17 - 특정 멤버 조회 수신 타입 - by leekoby */
export interface ApiFetchMemberResponse extends Member {}
/** 2023/05/17 - 특정 멤버 조회 핸들러 - by leekoby */
export interface ApiFetchMemberHandler {
  (body: ApiFetchMemberRequest): Promise<ApiFetchMemberResponse>;
}
