import { serverInstance } from ".";

// type
import type { ApiLogOutHandler, ApiLogOutResponse } from "@/types/api";

/** 2023/05/18 - 로그아웃 요청 - by 1-blue */
export const apiLogOut: ApiLogOutHandler = async ({}) => {
  const { data } = await serverInstance.post<ApiLogOutResponse>(`/logout`);

  return data;
};
