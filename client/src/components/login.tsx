/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const Input = ({ label, valid }: { label: string; valid: boolean }) => {
  return (
    <div className="w-full h-32">
      <div className="text-xl mb-1">{label}</div>
      <input
        type={label === "비밀번호" ? "password" : ""}
        placeholder={`${label}`}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
      />
      {valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </div>
  );
};

/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const SignInput = ({ label }: { label: string }) => {
  return (
    <div className="w-full h-32">
      <div className="text-xl mb-1">{label}</div>
      <input placeholder={`${label}`} className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl" />
    </div>
  );
};

/** 2023/05/05 - OAuth 로그인 버튼 - by Kadesti */
const OAuthCon = () => {
  return (
    <div className="w-4/5">
      <div className="bg-green-400 h-16 mb-6 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        로그인
      </div>
      <div className="bg-green-400 h-16 mb-6 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </div>
      <div className="bg-green-400 h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </div>
    </div>
  );
};

export { Input, OAuthCon, SignInput };
