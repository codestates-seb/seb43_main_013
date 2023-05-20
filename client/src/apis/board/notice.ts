import { serverInstance } from "..";

//type
import type {
  ApiFetchNoticeBoardHandler,
  ApiFetchNoticeBoardResponse,
  ApiCreateNoticeBoardHandler,
  ApiCreateNoticeBoardResponse,
  ApiUpdateNoticeBoardHandler,
  ApiUpdateNoticeBoardResponse,
  ApiDeleteNoticeBoardHandler,
  ApiDeleteNoticeBoardResponse,
  ApiFetchNoticeBoardListHandler,
  ApiFetchNoticeBoardListResponse,
} from "@/types/api";

/** 2023/05/20 - 공지사항 생성 요청 - by leekoby */
export const apiCreateNoticeBoard: ApiCreateNoticeBoardHandler = async (body) => {
  const { data } = await serverInstance.post<ApiCreateNoticeBoardResponse>(`/notice/new`, body);

  return data;
};
/** 2023/05/20 - 공지사항 상세 정보 패치 요청 - by leekoby */
export const apiFetchNoticeBoard: ApiFetchNoticeBoardHandler = async ({ noticeId }) => {
  const { data } = await serverInstance.get<ApiFetchNoticeBoardResponse>(`/notice/${noticeId}`);

  return data;
};
/** 2023/05/20 - 공지사항 수정 요청 - by leekoby */
export const apiUpdateNoticeBoard: ApiUpdateNoticeBoardHandler = async ({ noticeId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdateNoticeBoardResponse>(`/notice/${noticeId}`, body);

  return data;
};
/** 2023/05/20 - 공지사항 삭제 요청 - by leekoby */
export const apiDeleteNoticeBoard: ApiDeleteNoticeBoardHandler = async ({ noticeId }) => {
  const { data } = await serverInstance.delete<ApiDeleteNoticeBoardResponse>(`/notice/${noticeId}`);

  return data;
};
/** 2023/05/20 - 공지사항 게시글 리스트 요청 - by leekoby */
export const apiFetchNoticeBoardList: ApiFetchNoticeBoardListHandler = async ({ sorted, page, size }) => {
  const { data } = await serverInstance.get<ApiFetchNoticeBoardListResponse>(
    `/notices?sort=${sorted}&page=${page}&size=${size}`,
  );

  return data;
};
