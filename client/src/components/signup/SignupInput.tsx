import { useEffect } from "react";

interface mustType {
  label: string;
  value: string;
  setValue: React.Dispatch<string>;
  valid: boolean;
  setValid: React.Dispatch<boolean>;
}

const MustInput = ({ mustBind, isSubmit }: { mustBind: mustType; isSubmit: boolean }) => {
  const { label, value, setValue, valid, setValid } = mustBind;

  useEffect(() => {
    if (label === "이메일") {
      const emailRegExp = /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.com$/;
      const emailValid = emailRegExp.test(value);
      setValid(emailValid);
    } else if (value !== "") setValid(true);
    else setValid(false);
  }, [value]);

  return (
    <div className="w-full h-28">
      <div className="flex text-xl mb-1">{label + " (필수)"}</div>
      <input
        placeholder={label}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
        value={value}
        onChange={(e) => {
          setValue(e.target.value);
        }}
      />
      {isSubmit && !valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
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
    <div className="w-full h-28">
      <div className="flex text-xl mb-1">{label}</div>
      <input
        placeholder={label}
        type=""
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
        value={value}
        onChange={(e) => setValue(e.target.value)}
      />
    </div>
  );
};

export { MustInput, OptionalInput };
