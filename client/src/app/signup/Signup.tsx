import SignupForm from "../../components/signup/SignupForm2";

/** 2023/05/05 - 회원가입 페이지 컴포넌트 - by Kadesti */
const Signup = () => {
  return (
    <div className="w-full flex justify-center my-12 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-4xl mb-6">회원가입</h1>
        <SignupForm />
      </div>
    </div>
  );
};

export default Signup;
