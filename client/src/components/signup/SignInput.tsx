interface SignInputType {
  label: string;
  valid?: boolean;
  bind: {
    value: string;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  };
}

/** 2023/05/05 - 회원가입 인풋 창 - by Kadesti */
const SignInput = ({ label, valid, bind, dataset }: SignInputType) => {
  const mustIndex: number = ["이메일", "비밀번호", "이름", "닉네임"].findIndex((text) => text === label);
  const addText = mustIndex !== -1 ? "(필수)" : "";

  const [value, onChange] = dataset;

  return (
    <div className="w-full h-28">
      <div className="flex text-xl mb-1">{label + addText} </div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
        value={value}
        onChange={onChange}
      />
      {valid === false && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </div>
  );
};

export default SignInput;
