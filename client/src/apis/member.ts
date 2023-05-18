import { serverInstance } from ".";

// type
import type { ApiFetchMemberHandler, ApiFetchMemberResponse } from "@/types/api";

/** 2023/05/17 - 특정 멤버 조회 요청 - by 1-blue */
export const apiFetchMember: ApiFetchMemberHandler = async ({ memberId }) => {
  const { data } = await serverInstance.get<ApiFetchMemberResponse>(`/member/${memberId}`);

  return data;
};
