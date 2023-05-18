import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchMember } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchMemberRequest, ApiFetchMemberResponse } from "@/types/api";

/** 2023/05/17 - 특정 멤버를 조회하는 훅 - by 1-blue */
const useFetchMember = ({ memberId }: ApiFetchMemberRequest) => {
  return useQuery<ApiFetchMemberResponse>([QUERY_KEYS.member, memberId], () => apiFetchMember({ memberId }));
};

export { useFetchMember };
