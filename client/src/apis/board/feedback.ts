import { serverInstance } from "../";

// type
import type {
  ApiFetchFeedbackBoardHandler,
  ApiFetchFeedbackBoardResponse,
  ApiCreateFeedbackBoardHandler,
  ApiCreateFeedbackBoardResponse,
  ApiUpdateFeedbackBoardHandler,
  ApiUpdateFeedbackBoardResponse,
  ApiDeleteFeedbackBoardHandler,
  ApiDeleteFeedbackBoardResponse,
  ApiFetchFeedbackBoardListHandler,
  ApiFetchFeedbackBoardListResponse,
} from "@/types/api";

/** 2023/05/10 - 피드백 게시판 생성 요청 - by 1-blue */
export const apiCreateFeedbackBoard: ApiCreateFeedbackBoardHandler = async (body) => {
  const { data } = await serverInstance.post<ApiCreateFeedbackBoardResponse>(`/feedbackboard/new`, body);

  return data;
};
/** 2023/05/10 - 피드백 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiFetchFeedbackBoard: ApiFetchFeedbackBoardHandler = async ({ feedbackBoardId }) => {
  const { data } = await serverInstance.get<ApiFetchFeedbackBoardResponse>(`/feedbackboard/${feedbackBoardId}`);

  return data;
};
/** 2023/05/10 - 피드백 게시판 수정 요청 - by 1-blue */
export const apiUpdateFeedbackBoard: ApiUpdateFeedbackBoardHandler = async ({ feedbackBoardId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdateFeedbackBoardResponse>(
    `/feedbackboard/${feedbackBoardId}`,
    body,
  );

  return data;
};
/** 2023/05/10 - 피드백 게시판 삭제 요청 - by 1-blue */
export const apiDeleteFeedbackBoard: ApiDeleteFeedbackBoardHandler = async ({ feedbackBoardId }) => {
  const { data } = await serverInstance.delete<ApiDeleteFeedbackBoardResponse>(`/feedbackboard/${feedbackBoardId}`);

  return data;
};

/** 2023/05/12 - 피드백 게시판 게시글 리스트 요청 - by leekoby */
export const apiFetchFeedbackBoardList: ApiFetchFeedbackBoardListHandler = async ({
  selected,
  selectedFeedback,
  sorted,
  page,
  size,
}) => {
  const { data } = await serverInstance.get<ApiFetchFeedbackBoardListResponse>(
    `feedbackboards${selectedFeedback}${selected}?sort=${sorted}&page=${page}&size=${size}`,
  );

  return data;
};
