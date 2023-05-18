import { serverInstance } from ".";

// type
import type { ApiConfirmPasswordHandler, ApiConfirmPasswordResponse } from "@/types/api";

/** 2023/05/18 - 비밀번호 확인 요청 - by 1-blue */
export const apiConfirmPassword: ApiConfirmPasswordHandler = async ({ memberId, ...body }) => {
  const { data } = await serverInstance.post<ApiConfirmPasswordResponse>(`/member/${memberId}/password`, body);

  return data;
};
