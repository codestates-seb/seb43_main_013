import { serverInstance } from ".";

// type
import type {
  ApiCreateFollowHandler,
  ApiCreateFollowResponse,
  ApiDeleteFollowHandler,
  ApiDeleteFollowResponse,
} from "@/types/api";

/** 2023/05/18 - 특정 게시글 북마크 생성 요청 - by 1-blue */
export const apiCreateFollow: ApiCreateFollowHandler = async ({ memberId }) => {
  const { data } = await serverInstance.post<ApiCreateFollowResponse>(`/member/${memberId}/follow`);

  return data;
};

/** 2023/05/18 - 특정 게시글 북마크 제거 요청 - by 1-blue */
export const apiDeleteFollow: ApiDeleteFollowHandler = async ({ memberId }) => {
  const { data } = await serverInstance.delete<ApiDeleteFollowResponse>(`/member/${memberId}/follow`);

  return data;
};
