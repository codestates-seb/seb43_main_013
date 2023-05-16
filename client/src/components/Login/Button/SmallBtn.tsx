import Link from "next/link";

/** 2023/05/08 - 회원가입 비번 찾기 버튼 - by Kadesti */
const SmallBtn = () => {
  return (
    <div className="flex justify-end">
      <Link
        href="/signup"
        className="mb-6 bg-green-400 p-1 mr-2 flex justify-center items-center text-lg rounded-lg cursor-pointer hover:bg-green-200 hover:text-slate-400"
      >
        회원가입
      </Link>
      <Link
        href="/signup"
        className="mb-6 bg-green-400 p-1 flex justify-center items-center text-lg rounded-lg cursor-pointer hover:bg-green-200 hover:text-slate-400"
      >
        비밀번호 찾기
      </Link>
    </div>
  );
};

export default SmallBtn;
