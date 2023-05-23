//https://youtube-community.gitbook.io/youtube-community/api/undefined
export interface YoutubeList {
  videoCategory: string;
  videoId: number;
  youtubeUrl: string;
  thumbnailUrl: string;
  title: string;
  youtubeId: string;
}

/** 2023/05/22 - 유튜브 동영상 리스트 조회 요청 송신 타입 - by leekoby */
export interface ApiFetchYoutubeListRequest {
  youtubeCategoryId: number;
}
/** 2023/05/22 - 유튜브 동영상 리스트 조회 요청 수신 타입 - by leekoby */
export interface ApiFetchYoutubeListResponse {
  data: YoutubeList[];
}
/** 2023/05/22 - 유튜브 동영상 리스트 조회 요청 핸들러 - by leekoby */
export interface ApiFetchYoutubeListHandler {
  (body: ApiFetchYoutubeListRequest): Promise<ApiFetchYoutubeListResponse>;
}
