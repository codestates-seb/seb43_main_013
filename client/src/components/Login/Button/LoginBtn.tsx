/** 2023/05/10 - 로그인, OAuth 버튼 - by Kadesti */
const LoginBtn = ({ text }: { text: string }) => {
  return (
    <button
      className="mb-6 bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400"
      type="submit"
    >
      {text}
    </button>
  );
};

export default LoginBtn;
