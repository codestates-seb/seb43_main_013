"use client";
import useInputValid from "@/hooks/useInputValid";

interface label {
  label: string;
  id: number;
  valid?: boolean;
}

const LabelData = () => {
  const { idValid, pwValid } = useInputValid();

  const labelData: label[] = [
    {
      label: "아이디",
      valid: idValid,
      id: 0,
    },
    {
      label: "비밀번호",
      valid: pwValid,
      id: 1,
    },
    { label: "닉네임", id: 2 },
    { label: "유튜브 주소", id: 3 },
    { label: "이메일", id: 4 },
    { label: "휴대폰 번호", id: 5 },
    { label: "자기소개", id: 6 },
  ];

  return labelData;
};

export default LabelData;
