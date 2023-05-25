import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchFreeBoard } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchFreeBoardResponse } from "@/types/api";
interface Props {
  freeBoardId: number;
  initialData?: ApiFetchFreeBoardResponse;
}

/** 2023/05/10 - 자유 게시글 상세 정보 패치하는 훅 - by 1-blue */
const useFetchFreeBoard = ({ freeBoardId, initialData }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchFreeBoardResponse>(
    [QUERY_KEYS.freeBoard, freeBoardId],
    () => apiFetchFreeBoard({ freeBoardId }),
    { ...(initialData && { initialData }) },
  );

  return { data, isLoading };
};

export { useFetchFreeBoard };
