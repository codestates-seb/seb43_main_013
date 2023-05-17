import { serverInstance } from ".";

// type
import type {
  ApiCreateRecommentHandler,
  ApiCreateRecommentResponse,
  ApiDeleteRecommentHandler,
  ApiDeleteRecommentResponse,
  ApiUpdateRecommentHandler,
  ApiUpdateRecommentResponse,
} from "@/types/api";

/** 2023/05/13 - 답글 생성 요청 - by 1-blue */
export const apiCreateRecomment: ApiCreateRecommentHandler = async (type, { boardId, commentId, ...body }) => {
  const { data } = await serverInstance.post<ApiCreateRecommentResponse>(
    `/${type}board/${boardId}/comment/${commentId}/recomment/new`,
    body,
  );

  return data;
};

/** 2023/05/13 - 답글 수정 요청 - by 1-blue */
export const apiUpdateRecomment: ApiUpdateRecommentHandler = async (
  type,
  { boardId, commentId, recommentId, ...body },
) => {
  const { data } = await serverInstance.patch<ApiUpdateRecommentResponse>(
    `/${type}board/${boardId}/comment/${commentId}/recomment/${recommentId}`,
    body,
  );

  return data;
};

/** 2023/05/13 - 답글 삭제 요청 - by 1-blue */
export const apiDeleteRecomment: ApiDeleteRecommentHandler = async (type, { boardId, commentId, recommentId }) => {
  const { data } = await serverInstance.delete<ApiDeleteRecommentResponse>(
    `/${type}board/${boardId}/comment/${commentId}/recomment/${recommentId}`,
  );

  return data;
};
