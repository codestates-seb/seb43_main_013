"use client";

import { useCallback, useEffect, useRef } from "react";

/** 2023/05/11 - 닉네임 모달 외부 클릭 Hook - by Kadesti */
const useOutsideClick = (onClickOutside: () => void) => {
  const ref = useRef(null);

  const handleClickOutside = useCallback(
    (event) => {
      const inside = ref.current?.contains?.(event.target);
      if (ref.current && !inside) {
        onClickOutside();
      }
    },
    [onClickOutside, ref],
  );

  useEffect(() => {
    document.addEventListener("mouseup", handleClickOutside);

    return () => {
      document.removeEventListener("mouseup", handleClickOutside);
    };
  }, [handleClick]);

  return ref;
};

export default useOutsideClick;
