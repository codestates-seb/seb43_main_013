import Link from "next/link";

/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const Input = ({ label, valid }: { label: "아이디" | "비밀번호"; valid: boolean }) => {
  return (
    <div className="w-full h-28">
      <div className="text-xl flex mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
      />
      {valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </div>
  );
};

/** 2023/05/05 - 회원가입 인풋 창 - by Kadesti */
const SignInput = ({
  label,
  valid,
}: {
  label: "아이디" | "비밀번호" | "닉네임" | "유튜브 주소" | "이메일" | "휴대폰 번호" | "자기소개";
  valid?: boolean | undefined;
}) => {
  return (
    <button className="w-full h-28">
      <div className="flex text-xl mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
      />
      {valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </button>
  );
};

/** 2023/05/05 - OAuth 로그인 버튼 - by Kadesti */
const OAuthCon = () => {
  return (
    <div className="w-4/5 mt-3">
      <button className="mb-6 bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        로그인
      </button>
      <button className="mb-6 bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </button>
      <button className="mb-6 bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </button>
      <button className="bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </button>
    </div>
  );
};

/** 2023/05/08 - 회원가입 비번 찾기 버튼 - by Kadesti */
const SmallBtn = () => {
  return (
    <div className="flex self-end">
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
export { Input, OAuthCon, SignInput, SmallBtn };
