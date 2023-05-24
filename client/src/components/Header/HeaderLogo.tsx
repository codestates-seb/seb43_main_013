import React from "react";
import Link from "next/link";
import Logo4 from "@/public/logo/main-logo4.png";
import Image from "next/image";

/** 2023/05/10 - 헤더 로고 이미지 영역 - by Kadesti */
const HeaderLogo = () => {
  return (
    <Link href="/">
      <Image src={Logo4} alt="" className="mr-8" />
    </Link>
  );
};

export default HeaderLogo;
