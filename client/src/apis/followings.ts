import { serverInstance } from ".";

// type
import type { ApiFetchFollowingsHandler, ApiFetchFollowingsResponse } from "@/types/api";

/** 2023/05/18 - 특정 멤버의 팔로잉들 요청 - by 1-blue */
export const apiFetchFollowings: ApiFetchFollowingsHandler = async ({ memberId, ...params }) => {
  const { data } = await serverInstance.get<ApiFetchFollowingsResponse>(`/member/${memberId}/followings`, { params });

  return data;
};
