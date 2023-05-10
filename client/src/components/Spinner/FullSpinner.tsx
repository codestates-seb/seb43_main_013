"use client";

import { useEffect } from "react";

/** 2023/05/10 - 페이지 전체를 차지하는 스피너 - by 1-blue */
const FullSpinner = () => {
  /** 2023/05/10 - 외부 스크롤 금지 - by 1-blue */
  useEffect(() => {
    document.body.style.overflow = "hidden";

    return () => void (document.body.style.overflow = "auto");
  }, []);

  return (
    <article className="fixed inset-0 z-[2] bg-black/80 flex justify-center items-center">
      <div className="full-spinner -translate-x-1/2 -translate-y-1/2" />
    </article>
  );
};

export default FullSpinner;
