import { useEffect, useState } from "react";

interface mustType {
  label: string;
  value: string;
  setValue: React.Dispatch<string>;
  valid: boolean;
  setValid: React.Dispatch<boolean>;
}

const MustInput = ({ mustBind, isSubmit }: { mustBind: mustType; isSubmit: boolean }) => {
  const { label, value, setValue, valid, setValid } = mustBind;
  const [regText, setRegText] = useState("");

  const isMaster = label === "이메일" && value === "admin@gmail.com";

  useEffect(() => {
    if (label === "이메일") {
      const emailRegExp = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.com$/;
      const emailValid = emailRegExp.test(value);
      setValid(emailValid);
      setRegText("abc123@gmail.com");
    }
    if (label === "이름") {
      const nameRexExp = /^[가-힣a-zA-Z]+$/;
      const nameValid = nameRexExp.test(value);
      setValid(nameValid);
      setRegText("한글 또는 영어");
    }
    if (label === "전화번호") setRegText("010-0000-0000");

    if (value !== "") return setValid(true);
    return setValid(false);
  }, [value]);

  return (
    <div className="w-full h-24">
      <div className="flex text-xl mb-1">{label + " (필수)"}</div>
      <input
        placeholder={regText}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
        value={value}
        onChange={(e) => {
          setValue(e.target.value);
        }}
      />
      {isSubmit &&
        (isMaster ? (
          <div className="self-end text-rose-600">사용이 불가능한 계정입니다</div>
        ) : (
          !valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>
        ))}
    </div>
  );
};

interface OptionType {
  label: string;
  value: string;
  setValue: React.Dispatch<string>;
}
const OptionalInput = ({ optionBind }: { optionBind: OptionType }) => {
  const { label, value, setValue } = optionBind;

  return (
    <div className="w-full h-24">
      <div className="flex text-xl mb-1">{label}</div>
      <input
        placeholder={label === "전화번호" ? "010-0000-0000" : ""}
        type=""
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
    </div>
  );
};

export { MustInput, OptionalInput };
