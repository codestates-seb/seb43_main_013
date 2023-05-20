import { serverInstance } from ".";

// type
import type {
  ApiFetchMemberHandler,
  ApiFetchMemberResponse,
  ApiUpdateMemberHandler,
  ApiUpdateMemberResponse,
} from "@/types/api";

/** 2023/05/17 - 특정 멤버 조회 요청 - by 1-blue */
export const apiFetchMember: ApiFetchMemberHandler = async ({ memberId }) => {
  const { data } = await serverInstance.get<ApiFetchMemberResponse>(`/member/${memberId}`);

  return data;
};

/** 2023/05/18 - 특정 멤버 수정 요청 - by 1-blue */
export const apiUpdateMember: ApiUpdateMemberHandler = async ({ memberId, ...body }) => {
  const { data } = await serverInstance.patch<ApiUpdateMemberResponse>(`/member/${memberId}`, body);

  return data;
};
