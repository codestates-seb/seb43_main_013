import { serverInstance } from "../";

// type
import type {
  ApiFetchFreeBoardHandler,
  ApiFetchFreeBoardResponse,
  ApiCreateFreeBoardHandler,
  ApiCreateFreeBoardResponse,
  ApiUpdateFreeBoardHandler,
  ApiUpdateFreeBoardResponse,
  ApiDeleteFreeBoardHandler,
  ApiDeleteFreeBoardResponse,
} from "@/types/api";

/** 2023/05/10 - 자유 게시판 생성 요청 - by 1-blue */
export const apiCreateFreeBoard: ApiCreateFreeBoardHandler = async (body) => {
  const { data } = await serverInstance.post<ApiCreateFreeBoardResponse>(`/freeboard/new`, body);

  return data;
};
/** 2023/05/10 - 자유 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiFetchFreeBoard: ApiFetchFreeBoardHandler = async ({ freeBoardId }) => {
  const { data } = await serverInstance.get<ApiFetchFreeBoardResponse>(`/freeboard/${freeBoardId}`);

  return data;
};
/** 2023/05/10 - 자유 게시판 수정 요청 - by 1-blue */
export const apiUpdateFreeBoard: ApiUpdateFreeBoardHandler = async ({ freeBoardId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdateFreeBoardResponse>(`/freeboard/${freeBoardId}`, body);

  return data;
};
/** 2023/05/10 - 자유 게시판 삭제 요청 - by 1-blue */
export const apiDeleteFreeBoard: ApiDeleteFreeBoardHandler = async ({ freeBoardId }) => {
  const { data } = await serverInstance.delete<ApiDeleteFreeBoardResponse>(`/freeboard/${freeBoardId}`);

  return data;
};
