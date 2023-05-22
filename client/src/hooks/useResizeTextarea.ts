import { useCallback, useRef } from "react";

/** 2023/05/11 - textarea 리사이즈 - by 1-blue */
const useResizeTextarea = () => {
  /** 2023/05/11 - 로그인한 유저 정보 - by 1-blue */
  const textRef = useRef<null | HTMLTextAreaElement>(null);

  /** 2023/05/11 - 입력된 내용에 맞게 textarea 높이 지정 - by 1-blue */
  const handleResizeHeight = useCallback(() => {
    if (!textRef || !textRef.current) return;

    textRef.current.style.height = "0px";
    textRef.current.style.height = textRef.current.scrollHeight + "px";
  }, []);

  return [textRef, handleResizeHeight] as const;
};

export default useResizeTextarea;
