/** 2023/05/10 - 로그인, OAuth 버튼 - by Kadesti */
const LoginBtn = ({ text }: { text: string }) => {
  return (
    <button
      className="bg-main-500 text-white w-full h-16 my-4 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
      type="submit"
    >
      {text}
    </button>
  );
};

export default LoginBtn;
