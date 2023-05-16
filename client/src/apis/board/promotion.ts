import { serverInstance } from "../";

// type
import type {
  ApiFetchPromotionBoardHandler,
  ApiFetchPromotionBoardResponse,
  ApiCreatePromotionBoardHandler,
  ApiCreatePromotionBoardResponse,
  ApiUpdatePromotionBoardHandler,
  ApiUpdatePromotionBoardResponse,
  ApiDeletePromotionBoardHandler,
  ApiDeletePromotionBoardResponse,
  ApiFetchPromotionBoardListHandler,
  ApiFetchPromotionBoardListResponse,
} from "@/types/api";

/** 2023/05/10 - 홍보 게시판 생성 요청 - by 1-blue */
export const apiCreatePromotionBoard: ApiCreatePromotionBoardHandler = async (body) => {
  const { data } = await serverInstance.post<ApiCreatePromotionBoardResponse>(`/promotionboard/new`, body);

  return data;
};
/** 2023/05/10 - 홍보 게시판 상세 정보 패치 요청 - by 1-blue */
export const apiFetchPromotionBoard: ApiFetchPromotionBoardHandler = async ({ promotionBoardId }) => {
  const { data } = await serverInstance.get<ApiFetchPromotionBoardResponse>(`/promotionboard/${promotionBoardId}`);

  return data;
};
/** 2023/05/10 - 홍보 게시판 수정 요청 - by 1-blue */
export const apiUpdatePromotionBoard: ApiUpdatePromotionBoardHandler = async ({ promotionBoardId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdatePromotionBoardResponse>(
    `/promotionboard/${promotionBoardId}`,
    body,
  );

  return data;
};
/** 2023/05/10 - 홍보 게시판 삭제 요청 - by 1-blue */
export const apiDeletePromotionBoard: ApiDeletePromotionBoardHandler = async ({ promotionBoardId }) => {
  const { data } = await serverInstance.delete<ApiDeletePromotionBoardResponse>(`/promotionboard/${promotionBoardId}`);

  return data;
};

/** 2023/05/12 - 홍보 게시판 게시글 리스트 요청 - by leekoby */
export const apiFetchPromotionBoardList: ApiFetchPromotionBoardListHandler = async ({ page, size }) => {
  const { data } = await serverInstance.get<ApiFetchPromotionBoardListResponse>(
    `/promotionboards?page=${page}&size=${size}`,
  );

  return data;
};
