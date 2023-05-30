"use client";
import useCustomToast from "@/hooks/useCustomToast";
import { useLoadingStore } from "@/store";
import axios from "axios";
import { useRouter } from "next/navigation";
const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

import { useState } from "react";

/** 2023/05/13 - 회원가입 데이터 바인딩 및 submit 함수 - by Kadesti */
const SignupBind = () => {
  const toast = useCustomToast();
  const { loading } = useLoadingStore();
  const router = useRouter();

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

    setIsSubmit(true);
    const data = { email, password, name, nickname, phone, link, introduction, profileImageUrl };

    try {
      loading.start();
      if (email === "admin@gmail.com") return toast({ title: "회원가입에 실패했습니다.", status: "error" });

      await axios.post(`${baseUrl}/api/signup`, data);
      toast({ title: "회원가입에 성공했습니다.\n로그인 페이지로 이동됩니다.", status: "success" });

      router.replace("/login");
    } catch (error: any) {
      if (error.response) {
        const fieldErrors = error.response.data.fieldErrors;
        if (error.response.data.message === "The phone is already registered. Please use a different phone.") {
          return toast({ title: "전화번호가 올바르지 않습니다", status: "error" });
        }
        if (fieldErrors === null) toast({ title: "올바른 값을 입력해주세요", status: "error" });
        toast({ title: fieldErrors[0].reason, status: "error" });
      }
    } finally {
      loading.end();
    }
  };

  return { mustBind, optionBind, profileImageUrl, setProfileImageUrl, onsubmit, isSubmit };
};
export default SignupBind;
