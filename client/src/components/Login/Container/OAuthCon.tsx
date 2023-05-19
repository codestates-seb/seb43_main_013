import LoginBtn from "../Button/LoginBtn";
import Image from "next/image";

import 구글로그인 from "/src/public/images/google.svg";
import 네이버로그인 from "/src/public/images/naver.svg";
import 카카오로그인 from "/src/public/images/kakao.svg";
import axios from "axios";

/** 2023/05/10 - 로그인 버튼 컨테이너  - by Kadesti */
const OAuthCon = () => {
  const btnText: string[] = ["Google", "Naver", "kakao"];

  const kakaoLogin = async () => {
    const token = await axios.get(
      "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback",
    );
    // redirect url
    // /api/login/oauth2?access_token=xxx&refresh_token=xxx
  };

  const googleLogin = async () => {
    const token = await axios.get("http://localhost:8080/oauth2/authorization/google");
    // redirect url
    // /api/login/oauth2?access_token=xxx&refresh_token=xxx
  };

  const naverLogin = async () => {
    const token = await axios.get(
      "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=http://localhost:8080/auth/naver/callback",
    );
    // redirect url
    // /api/login/oauth2?access_token=xxx&refresh_token=xxx
  };

  return (
    <ul>
      <li
        className="mb-6 bg-white-400 border-2 border-black w-full h-16 flex justify-center items-center  rounded-2xl cursor-pointer hover:opacity-50"
        onClick={() => {}}
      >
        <Image src={구글로그인} alt="" className="h-14 w-14 aspect-square cursor-pointer hover:opacity-50" />
        <span className="text-3xl">구글 로그인</span>
      </li>
      <li
        className="mb-6 bg-green-400 text-white w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
        onClick={() => {}}
      >
        <Image src={네이버로그인} alt="" className="h-14 w-14 aspect-square cursor-pointer hover:opacity-50" />
        네이버 로그인
      </li>
      <li
        className="mb-6 bg-yellow-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
        onClick={() => {}}
      >
        <Image src={카카오로그인} alt="" className="h-14 w-14 aspect-square cursor-pointer hover:opacity-50" />
        카카오 로그인
      </li>
    </ul>
  );
};

export default OAuthCon;
