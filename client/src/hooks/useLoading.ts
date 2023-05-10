import { useContext } from "react";

import { LoadingContext } from "@/context/LoadingProvider";

/** 2023/05/10 - 로딩 처리를 위한 Context API의 값을 얻는 훅 - by 1-blue */
const useLoading = () => {
  const result = useContext(LoadingContext);

  if (!result) {
    throw new Error("useLoading에 대한 Context API 없음");
  }

  return result;
};

export default useLoading;
