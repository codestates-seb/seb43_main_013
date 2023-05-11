"use client";
import { useState } from "react";

import axios from "axios";
import SignContainer from "./SignContainer";
import LabelData from "./labelData";
import useSignupSubmit from "@/hooks/useSignupSubmit";

/** 2023/05/05 - 회원가입 Form - by Kadesti */
const SignUpForm = () => {
  const label = LabelData();

  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [phone, setPhone] = useState("");
  const [link, setLink] = useState("");
  const [introduction, setIntroduction] = useState("");
  const [profileImageUrl, setProfileImageUrl] = useState("");

  const bindData = [
    { email, setEmail },
    { name, setName },
    { password, setPassword },
    { nickname, setNickname },
    { phone, setPhone },
    { link, setLink },
    { introduction, setIntroduction },
    { profileImageUrl, setProfileImageUrl },
  ];

  const signupSubmit = () => {
    const body = {
      email,
      name,
      password,
      nickname,
      phone,
      link,
      introduction,
      profileImageUrl,
    };

    axios.post("/api/signup", { ...body });
  };

  return (
    <form className="w-full flex flex-col items-center" onSubmit={signupSubmit}>
      <SignContainer label={label} bindData={bindData} />
      <div className="w-full h-44 border-2 border-black flex justify-center items-center rounded-lg mt-6 mb-3 text-2xl">
        사진
      </div>
      <button className="w-4/5 h-16 mt-5 mb-6 flex justify-center items-center text-3xl rounded-2xl bg-green-400 hover:bg-green-200 hover:text-slate-400">
        회원가입
      </button>
      {/* <button
        disabled={disabled}
        className={`w-4/5 h-16 mt-5 mb-6 flex justify-center items-center text-3xl rounded-2xl ${
          disabled ? "bg-slate-300 text-white" : "bg-green-400 hover:bg-green-200 hover:text-slate-400"
        }`}
      > */}
    </form>
  );
};

export default SignUpForm;
