export * from "./free";
export * from "./feedback";
export * from "./promotion";
export * from "./job";
export * from "./written";
export * from "./bookmarked";
export * from "./liked";
export * from "./notice";

/** 2023/05/11 - 보드 공통 타입 - by 1-blue */
export interface Board {
  title: string; // 게시글 제목
  content: string; // 게시글 내용
  commentCount: number; // 댓글수
  likeCount: number; // 좋아요수
  viewCount: number; // 조회수
  createdAt: Date; // 작성 시간
  modifiedAt: Date; // 수정 시간
  memberId: number;
  email: string; // 작성자 이메일
  nickname: string; // 작성자 닉네임
  profileImageUrl: string; // 작성자 프로필 이미지
  bookmarked?: boolean;
  liked?: boolean;
}

/** 2023/05/17 - 프로필 페이지에서 사용하는 보드 타입 - by 1-blue */
export interface BoardOfProfile extends Board {
  id: number;
  categoryName?: string;
  boardType: string;
}

/** 2023/05/16 - 태그 공통 타입 - by 1-blue */
export interface Tag {
  tagName: string;
}
/** 2023/05/16 - 상세 게시글에서 사용하는 태그 공통 타입 - by 1-blue */
export interface DetailTag {
  tagId: number;
  tagName: string;
}
