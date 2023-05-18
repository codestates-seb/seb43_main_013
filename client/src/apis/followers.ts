import { serverInstance } from ".";

// type
import type { ApiFetchFollowersHandler, ApiFetchFollowersResponse } from "@/types/api";

/** 2023/05/18 - 특정 멤버의 팔로워들 요청 - by 1-blue */
export const apiFetchFollowers: ApiFetchFollowersHandler = async ({ memberId, ...params }) => {
  const { data } = await serverInstance.get<ApiFetchFollowersResponse>(`/member/${memberId}/followers`, { params });

  return data;
};
