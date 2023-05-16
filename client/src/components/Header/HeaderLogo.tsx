import Link from "next/link";

/** 2023/05/10 - 헤더 로고 이미지 영역 - by Kadesti */
const HeaderLogo = () => {
  return (
    <Link href="/">
      <h1 className="text-6xl mr-10 cursor-pointer">CC</h1>
    </Link>
  );
};

export default HeaderLogo;
