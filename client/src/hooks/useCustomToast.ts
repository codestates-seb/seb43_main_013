"use client";

import { UseToastOptions, useToast } from "@chakra-ui/react";

/** 2023/05/17 - 기본 설정을 적용한 "chakra-ui"의 "toast" - by 1-blue */
const useCustomToast = () => {
  const chakraToast = useToast();

  const toast = (option: UseToastOptions | undefined) =>
    chakraToast({
      ...option,
      duration: 2500,
      isClosable: true,
      position: "top",
    });

  return toast;
};

export default useCustomToast;
