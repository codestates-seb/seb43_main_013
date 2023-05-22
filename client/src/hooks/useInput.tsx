"use client";
import { useState } from "react";

const useInput = () => {
  const [value, setValue] = useState("");

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: newValue },
    } = event;
    setValue(newValue);
  };
  return { value, onChange };
};

export default useInput;
