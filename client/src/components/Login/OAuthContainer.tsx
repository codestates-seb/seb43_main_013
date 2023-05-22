"use client";

import Link from "next/link";

const OAuthContainer = () => {
  const googleLink = "http://localhost:8080/oauth2/authorization/google";
  const naverLink =
    "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=JAnr85GxwFcBiBMCvdpL&state=vninaeonfd&redirect_uri=http://localhost:8080/auth/naver/callback";
  const kakaoLink =
    "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d7774b0de8bd81c958657f202701d306&redirect_uri=http://localhost:8080/auth/kakao/callback";
  return (
    <>
      <Link
        href={googleLink}
        className="my-6 bg-main-500 text-white w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
      >
        구글 로그인
      </Link>
      <Link
        href={naverLink}
        className="my-6 bg-main-500 text-white w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
      >
        네이버 로그인
      </Link>
      <Link
        href={kakaoLink}
        className="my-6 bg-main-500 text-white w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
      >
        카카오 로그인
      </Link>
    </>
  );
};

export default OAuthContainer;
