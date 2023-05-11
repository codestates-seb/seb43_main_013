"use client";
import LabelData from "../../components/signup/labelData";
import SignUpForm from "../../components/signup/SignUpForm";

/** 2023/05/05 - 회원가입 페이지 컴포넌트 - by Kadesti */
const Signup = () => {
  const label = LabelData;

  return (
    <div className="w-full flex justify-center mt-16 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">회원가입</h1>
        <SignUpForm />
      </div>
    </div>
  );
};

export default Signup;
