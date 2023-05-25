import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchPromotionBoard } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchPromotionBoardResponse } from "@/types/api";
interface Props {
  promotionBoardId: number;
  initialData?: ApiFetchPromotionBoardResponse;
}

/** 2023/05/10 - 홍보 게시글 상세 정보 패치하는 훅 - by 1-blue */
const useFetchPromotionBoard = ({ promotionBoardId, initialData }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchPromotionBoardResponse>(
    [QUERY_KEYS.promotionBoard, promotionBoardId],
    () => apiFetchPromotionBoard({ promotionBoardId }),
    { ...(initialData && { initialData }) },
  );

  return { data, isLoading };
};

export { useFetchPromotionBoard };
