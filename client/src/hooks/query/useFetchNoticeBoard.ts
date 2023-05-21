import { useQuery } from "@tanstack/react-query";

// api
import { apiFetchNoticeBoard } from "@/apis";

// key
import { QUERY_KEYS } from "@/hooks/query";

// type
import type { ApiFetchNoticeBoardResponse } from "@/types/api";

interface Props {
  noticeId: number;
}

/** 2023/05/20 - 공지사항 게시글 상세 정보 패치하는 훅 - by leekoby */
const useFetchNoticeBoard = ({ noticeId }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchNoticeBoardResponse>([QUERY_KEYS.noticeBoard, noticeId], () =>
    apiFetchNoticeBoard({ noticeId }),
  );

  return { data, isLoading };
};

export { useFetchNoticeBoard };
