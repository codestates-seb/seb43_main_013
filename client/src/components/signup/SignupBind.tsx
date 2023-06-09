"use client";
import axios from "axios";
const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

import { useState } from "react";

/** 2023/05/13 - 회원가입 데이터 바인딩 및 submit 함수 - by Kadesti */
const SignupBind = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [nickname, setNickname] = useState("");

  const [emailValid, setEmailValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [nameValid, setNameValid] = useState(false);
  const [nicknameValid, setNicknameValid] = useState(false);

  const mustBind = [
    { label: "이메일", value: email, setValue: setEmail, valid: emailValid, setValid: setEmailValid },
    { label: "비밀번호", value: password, setValue: setPassword, valid: passwordValid, setValid: setPasswordValid },
    { label: "이름", value: name, setValue: setName, valid: nameValid, setValid: setNameValid },
    { label: "닉네임", value: nickname, setValue: setNickname, valid: nicknameValid, setValid: setNicknameValid },
  ];

  const [phone, setPhone] = useState("");
  const [link, setLink] = useState("");
  const [introduction, setIntroduction] = useState("");

  const optionBind = [
    { label: "전화번호", value: phone, setValue: setPhone },
    { label: "유튜브 링크", value: link, setValue: setLink },
    { label: "자기소개", value: introduction, setValue: setIntroduction },
  ];

  const [profileImageUrl, setProfileImageUrl] = useState("");

  const [isSubmit, setIsSubmit] = useState(false);

  const onsubmit = async () => {
    if (email === "" || password == "" || name === "" || nickname === "") return setIsSubmit(true);

    const data = { email, password, name, nickname, phone, link, introduction, profileImageUrl };

    try {
      const response = await axios.post(`${baseUrl}/api/signup`, data);
      console.log(response);
    } catch (error) {
      alert(error);
    }
  };

  return { mustBind, optionBind, profileImageUrl, setProfileImageUrl, onsubmit, isSubmit };
};
export default SignupBind;
