"use client";
import { useState } from "react";

/** 2023/05/05 - 로그인 유효성 체크 상태값 - by Kadesti */
const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, setIdValid, pwValid, setPWValid };
};

export default useInputValid;
