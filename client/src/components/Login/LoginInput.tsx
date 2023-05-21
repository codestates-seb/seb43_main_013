"use client";
import { useEffect, useState } from "react";
import SmallBtn from "./Button/SmallBtn";

interface login {
  label: "이메일" | "비밀번호";
  value: string;
  setValue: React.Dispatch<string>;
  submitCnt: number;
}

/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const LoginInput = ({ label, value, setValue, submitCnt }: login) => {
  const height = label === "비밀번호" ? "h-22" : "h-28";

  const [message, setMessage] = useState("");

  useEffect(() => {
    if (submitCnt === 0) return setMessage("");

    if (value === "") {
      if (label === "이메일") return setMessage(`이메일을 작성해주세요`);
      if (label === "비밀번호") return setMessage(`비밀번호를 작성해주세요`);
    } else if (label === "비밀번호") return setMessage("");

    const emailRegExp = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.com$/;
    if (!emailRegExp.test(value)) return setMessage("이메일을 양식에 맞게 작성해주세요");
    else return setMessage("");
  }, [submitCnt]);

  return (
    <div className={`w-full ${height}`}>
      <div className="text-xl flex mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-main-500 rounded-lg p-3 text-xl placeholder-main-300 outline-none"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />

      <div className="flex justify-between">
        {<span className="text-lg text-main-400">{message}</span>}
        {label === "비밀번호" && <SmallBtn />}
      </div>
    </div>
  );
};

export default LoginInput;
