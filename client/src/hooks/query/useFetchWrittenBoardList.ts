import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchWrittenBoardList } from "@/apis";

// type
import type { ApiFetchWrittenBoardListRequest, ApiFetchWrittenBoardListResponse } from "@/types/api";

/** 2023/05/17 - 내가 작성한 게시글 리스트 패치하는 훅 - by leekoby */
const useFetchWrittenBoardList = ({ memberId, page = 1, size }: ApiFetchWrittenBoardListRequest) => {
  const { data, isFetching } = useQuery<ApiFetchWrittenBoardListResponse>(
    ["profile", "board", "written", memberId, page],
    () => apiFetchWrittenBoardList({ memberId, page, size }),
  );

  return { data, isFetching };
};

export { useFetchWrittenBoardList };
