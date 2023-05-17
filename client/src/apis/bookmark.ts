import { serverInstance } from ".";

// type
import type {
  ApiCreateBookmarkHandler,
  ApiCreateBookmarkResponse,
  ApiDeleteBookmarkHandler,
  ApiDeleteBookmarkResponse,
} from "@/types/api";

/** 2023/05/17 - 특정 게시글 북마크 생성 요청 - by 1-blue */
export const apiCreateBookmark: ApiCreateBookmarkHandler = async (type, { boardId }) => {
  const { data } = await serverInstance.post<ApiCreateBookmarkResponse>(`/${type}board/${boardId}/bookmark`);

  return data;
};

/** 2023/05/17 - 특정 게시글 북마크 제거 요청 - by 1-blue */
export const apiDeleteBookmark: ApiDeleteBookmarkHandler = async (type, { boardId }) => {
  const { data } = await serverInstance.delete<ApiDeleteBookmarkResponse>(`/${type}board/${boardId}/bookmark`);

  return data;
};
