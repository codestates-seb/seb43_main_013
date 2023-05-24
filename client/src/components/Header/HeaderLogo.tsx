import React from "react";
import Link from "next/link";
import Logo from "@/public/logo/main-logo.png";
import Image from "next/image";

/** 2023/05/10 - 헤더 로고 이미지 영역 - by Kadesti */
const HeaderLogo = () => {
  return (
    <Link href="/" className="mr-8 p-2">
      <Image src={Logo} alt="" />
    </Link>
  );
};

export default HeaderLogo;
