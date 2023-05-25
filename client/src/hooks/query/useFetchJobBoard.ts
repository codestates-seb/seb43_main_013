import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchJobBoard } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchJobBoardResponse } from "@/types/api";
interface Props {
  jobBoardId: number;
  initialData?: ApiFetchJobBoardResponse;
}

/** 2023/05/10 - 구인구직 게시글 상세 정보 패치하는 훅 - by 1-blue */
const useFetchJobBoard = ({ jobBoardId, initialData }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchJobBoardResponse>(
    [QUERY_KEYS.jobBoard, jobBoardId],
    () => apiFetchJobBoard({ jobBoardId }),
    { ...(initialData && { initialData }) },
  );

  return { data, isLoading };
};

export { useFetchJobBoard };
