import { serverInstance } from "../";

// type
import type {
  ApiFetchJobBoardHandler,
  ApiFetchJobBoardResponse,
  ApiCreateJobBoardHandler,
  ApiCreateJobBoardResponse,
  ApiUpdateJobBoardHandler,
  ApiUpdateJobBoardResponse,
  ApiDeleteJobBoardHandler,
  ApiDeleteJobBoardResponse,
} from "@/types/api";

/** 2023/05/10 - 구인구직 게시판 생성 요청 - by 1-blue */
export const apiCreateJobBoard: ApiCreateJobBoardHandler = async (body) => {
  const { data } = await serverInstance.post<ApiCreateJobBoardResponse>(`/jobboard/new`, body);

  return data;
};
/** 2023/05/10 - 구인구직 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiFetchJobBoard: ApiFetchJobBoardHandler = async ({ jobBoardId }) => {
  const { data } = await serverInstance.get<ApiFetchJobBoardResponse>(`/jobboard/${jobBoardId}`);

  return data;
};
/** 2023/05/10 - 구인구직 게시판 수정 요청 - by 1-blue */
export const apiUpdateJobBoard: ApiUpdateJobBoardHandler = async ({ jobBoardId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdateJobBoardResponse>(`/jobboard/${jobBoardId}`, body);

  return data;
};
/** 2023/05/10 - 구인구직 게시판 삭제 요청 - by 1-blue */
export const apiDeleteJobBoard: ApiDeleteJobBoardHandler = async ({ jobBoardId }) => {
  const { data } = await serverInstance.delete<ApiDeleteJobBoardResponse>(`/jobboard/${jobBoardId}`);

  return data;
};
