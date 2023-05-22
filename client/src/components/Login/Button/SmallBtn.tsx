import Link from "next/link";

/** 2023/05/08 - 회원가입 비번 찾기 버튼 - by Kadesti */
const SmallBtn = () => {
  return (
    <span>
      <Link
        href="/signup"
        className="h-8 mb-2 text-lg cursor-pointer text-main-500 hover:border-b-2 hover:border-main-500 mr-4"
      >
        회원가입
      </Link>
      <Link
        href="/signup"
        className="h-8  mb-2 text-lg cursor-pointer text-main-500 hover:border-b-2 hover:border-main-500"
      >
        비밀번호 찾기
      </Link>
    </span>
  );
};

export default SmallBtn;
