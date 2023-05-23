import { serverInstance } from ".";

// type
import type { ApiLogOutHandler, ApiLogOutResponse, ApiSignOutHandler, ApiSignOutResponse } from "@/types/api";

/** 2023/05/18 - 로그아웃 요청 - by 1-blue */
export const apiLogOut: ApiLogOutHandler = async ({}) => {
  const { data } = await serverInstance.post<ApiLogOutResponse>(`/logout`);

  return data;
};

/** 2023/05/24 - 회원탈퇴 요청 - by 1-blue */
export const apiSignOut: ApiSignOutHandler = async ({ memberId }) => {
  const { data } = await serverInstance.delete<ApiSignOutResponse>(`/member/${memberId}`);

  return data;
};
