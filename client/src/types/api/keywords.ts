import type { PageInfo } from ".";

// ============================== 키워드 요청 요청 ==============================
/** 2023/05/23 - 키워드 요청 요청 송신 타입 - by 1-blue */
export interface ApiFetchKeywordsRequest {}
/** 2023/05/23 - 키워드 요청 요청 수신 타입 - by 1-blue */
export interface ApiFetchKeywordsResponse {
  data: string[];
  pageInfo: PageInfo;
}
/** 2023/05/23 - 키워드 요청 요청 핸들러 - by 1-blue */
export interface ApiFetchKeywordsHandler {
  (body: ApiFetchKeywordsRequest): Promise<ApiFetchKeywordsResponse>;
}
