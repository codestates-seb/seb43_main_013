"use client";
import SignInput from "./SignInput";
import label from "./labelType";

/** 2023/05/10 - 회원가입 인풋 컨테이너 - by Kadesti */
const SignContainer = ({ label, bindData }: { label: label[]; bindData: [] }) => {
  const dataBind = Object.values(bindData);

  return (
    <>
      {label.map((el, idx) => {
        return <SignInput label={el.label} valid={el.valid} bind={el.bind} key={el.id} dataset={dataBind[idx]} />;
      })}
    </>
  );
};

export default SignContainer;
