import Link from "next/link";
const SignupRouteBtn = () => {
  return (
    <Link
      href="/signup"
      className="bg-main-500 text-white w-full h-16 mb-4 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
      type="submit"
    >
      회원가입
    </Link>
  );
};

export default SignupRouteBtn;
