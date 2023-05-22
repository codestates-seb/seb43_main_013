import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchFeedbackBoard } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchFeedbackBoardResponse } from "@/types/api";
interface Props {
  feedbackBoardId: number;
}

/** 2023/05/10 - 피드백 게시글 상세 정보 패치하는 훅 - by 1-blue */
const useFetchFeedbackBoard = ({ feedbackBoardId }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchFeedbackBoardResponse>([QUERY_KEYS.feedbackBoard, feedbackBoardId], () =>
    apiFetchFeedbackBoard({ feedbackBoardId }),
  );

  return { data, isLoading };
};

export { useFetchFeedbackBoard };
