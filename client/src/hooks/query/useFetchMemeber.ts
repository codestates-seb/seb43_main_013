import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchMember } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchMemberRequest, ApiFetchMemberResponse } from "@/types/api";

interface Props extends ApiFetchMemberRequest {
  initialData?: ApiFetchMemberResponse;
}

/** 2023/05/17 - 특정 멤버를 조회하는 훅 - by 1-blue */
const useFetchMember = ({ memberId, initialData }: Props) => {
  return useQuery<ApiFetchMemberResponse>([QUERY_KEYS.member, memberId], () => apiFetchMember({ memberId }), {
    ...(initialData && { initialData }),
  });
};

export { useFetchMember };
