import { serverInstance } from ".";

// type
import type {
  ApiCreateLikeOfPostHandler,
  ApiCreateLikeOfPostResponse,
  ApiDeleteLikeOfPostHandler,
  ApiDeleteLikeOfPostResponse,
} from "@/types/api";

/** 2023/05/17 - 특정 게시글 좋아요 생성 요청 - by 1-blue */
export const apiCreateLikeOfPost: ApiCreateLikeOfPostHandler = async (type, { boardId }) => {
  const { data } = await serverInstance.post<ApiCreateLikeOfPostResponse>(`/${type}board/${boardId}/like`);

  return data;
};

/** 2023/05/17 - 특정 게시글 좋아요 제거 요청 - by 1-blue */
export const apiDeleteLikeOfPost: ApiDeleteLikeOfPostHandler = async (type, { boardId }) => {
  const { data } = await serverInstance.delete<ApiDeleteLikeOfPostResponse>(`/${type}board/${boardId}/like`);

  return data;
};
