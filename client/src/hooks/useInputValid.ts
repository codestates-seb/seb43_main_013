/** 2023/05/05 - 로그인 유효성에 써먹어 보려고 했는데 실패 - by Kadesti */
"use client";
import { useState } from "react";

const useInputValid = () => {
  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPWValid] = useState(false);

  return { idValid, pwValid };
};

export default useInputValid;
