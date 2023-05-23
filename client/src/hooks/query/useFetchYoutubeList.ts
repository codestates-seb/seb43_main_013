import { useQuery } from "@tanstack/react-query";

//api
import { apiFetchYoutubeList } from "@/apis";

//key
import { QUERY_KEYS } from ".";

//type

import type { ApiFetchYoutubeListResponse } from "@/types/api";

interface Props {
  youtubeCategoryId: number;
}

/** 2023/05/22- 유튜브 인기동영상 리스트 패치하는 훅 - by leekoby */
const useFetchYoutubeList = ({ youtubeCategoryId }: Props) => {
  const { data, isLoading } = useQuery<ApiFetchYoutubeListResponse>([QUERY_KEYS.youtubeList, youtubeCategoryId], () =>
    apiFetchYoutubeList({ youtubeCategoryId }),
  );
  return { data, isLoading };
};

export { useFetchYoutubeList };
