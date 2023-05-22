import { serverInstance } from ".";

// type
import type {
  ApiCreateCommentHandler,
  ApiCreateCommentResponse,
  ApiDeleteCommentHandler,
  ApiDeleteCommentResponse,
  ApiFetchCommentsHandler,
  ApiFetchCommentsResponse,
  ApiUpdateCommentHandler,
  ApiUpdateCommentResponse,
} from "@/types/api";

/** 2023/05/11 - 댓글들 패치 요청 - by 1-blue */
export const apiFetchComments: ApiFetchCommentsHandler = async (type, { boardId, ...body }) => {
  const { data } = await serverInstance.get<ApiFetchCommentsResponse>(`/${type}board/${boardId}/comments`, {
    params: body,
  });

  return data;
};

/** 2023/05/11 - 댓글 생성 요청 - by 1-blue */
export const apiCreateComment: ApiCreateCommentHandler = async (type, { boardId, ...body }) => {
  const { data } = await serverInstance.post<ApiCreateCommentResponse>(`/${type}board/${boardId}/comment/new`, body);

  return data;
};

/** 2023/05/11 - 댓글 수정 요청 - by 1-blue */
export const apiUpdateComment: ApiUpdateCommentHandler = async (type, { commentId, boardId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdateCommentResponse>(
    `/${type}board/${boardId}/comment/${commentId}`,
    body,
  );

  return data;
};

/** 2023/05/11 - 댓글 삭제 요청 - by 1-blue */
export const apiDeleteComment: ApiDeleteCommentHandler = async (type, { commentId, boardId }) => {
  const { data } = await serverInstance.delete<ApiDeleteCommentResponse>(
    `/${type}board/${boardId}/comment/${commentId}`,
  );

  return data;
};
