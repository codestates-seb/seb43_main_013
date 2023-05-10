/** 2023/05/05 - 로그인 인풋 창 - by Kadesti */
const LoginInput = ({ label, valid }: { label: "아이디" | "비밀번호"; valid: boolean }) => {
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

export default LoginInput;
