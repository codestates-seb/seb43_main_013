"use client";
import { useState } from "react";

/** 2023/05/11 - 회원가입 Submit 에 활용될 데이터 Hooks - by Kadesti */
const useSignupSubmit = () => {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [phone, setPhone] = useState("");
  const [link, setLink] = useState("");
  const [introduction, setIntroduction] = useState("");
  const [profileImageUrl, setProfileImageUrl] = useState("");

  return {
    email,
    setEmail,
    name,
    setName,
    password,
    setPassword,
    nickname,
    setNickname,
    phone,
    setPhone,
    link,
    setLink,
    introduction,
    setIntroduction,
    profileImageUrl,
    setProfileImageUrl,
  };
};

export default useSignupSubmit;
