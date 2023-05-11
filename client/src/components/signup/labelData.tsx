"use client";
import useInputValid from "@/hooks/useInputValid";
import totalBind from "./InputBind";
import label from "./labelType";

/** 2023/05/11 - 회원가입 인풋 라벨 데이터  - by Kadesti */
const LabelData = () => {
  const { idValid, pwValid } = useInputValid();
  const { emailBind, pwBind, nameBind, nicknameBind, phoneBind, linkBind, introBind, imageBind } = totalBind;

  const labelData: label[] = [
    {
      label: "이메일",
      valid: idValid,
      bind: emailBind,
      id: 0,
    },
    {
      label: "비밀번호",
      valid: pwValid,
      bind: pwBind,
      id: 1,
    },
    { label: "이름", bind: nameBind, id: 2 },
    { label: "닉네임", bind: nicknameBind, id: 3 },
    { label: "유튜브 주소", bind: phoneBind, id: 4 },
    { label: "휴대폰 번호", bind: linkBind, id: 5 },
    { label: "자기소개", bind: introBind, id: 6 },
    { label: "프로필 이미지", bind: imageBind, id: 7 },
  ];

  return labelData;
};

export default LabelData;

// interface label {
//   email: String; // * 이메일
//   name: String; // * 이름
//   password: String; // * 비밀번호
//   nickname: String; // * 닉네임
//   phone?: String; // 전화번호
//   link?: String; // 유튜브 링크
//   introduction?: String; // 자기소개 글
//   profileImageUrl?: String; // 사용자 프로필 이미지(사용자가 직접 설정하지 않으면 디폴트 이미지로 설정)
// }
