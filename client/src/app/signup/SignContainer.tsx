import LabelData from "./LabelData";

type signcon = () => JSX.Element;
/** 2023/05/10 - 회원가입 인풋 컨테이너 - by Kadesti */
const SignContainer: signcon = () => {
  const label = LabelData();
  //   console.log("label.length: ", label.length);

  <>
    {label.map((el) => (
      <SignInput label={el.label} valid={el.valid} key={el.id} />
    ))}
  </>;
};

export default SignContainer;

/** 2023/05/05 - 회원가입 인풋 창 - by Kadesti */
const SignInput = ({ label, valid }: { label: string; valid?: boolean }) => {
  return (
    <div className="w-full h-28">
      <div className="flex text-xl mb-1">{label}</div>
      <input
        placeholder={`${label}`}
        type={label === "비밀번호" ? "password" : ""}
        className="w-full h-12 border-2 border-black rounded-lg p-3 text-xl"
      />
      {!valid && <div className="self-end text-rose-600">형식에 맞게 입력해주세요!</div>}
    </div>
  );
};
