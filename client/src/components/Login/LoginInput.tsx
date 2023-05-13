interface login {
  label: "이메일" | "비밀번호";
  value: string;
  setValue: React.Dispatch<string>;
}

/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const LoginInput = ({ label, value, setValue }: login) => {
  return (
    <div className="w-full h-28">
      <div className="text-xl flex mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
      {/* {valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>} */}
    </div>
  );
};

export default LoginInput;
