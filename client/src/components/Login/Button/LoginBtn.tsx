/** 2023/05/10 - 로그인, OAuth 버튼 - by Kadesti */
const LoginBtn = ({ text }: { text: string }) => {
  return (
    <button
      className="my-6 bg-main-400 text-white w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:opacity-50"
      onClick={() => {}}
    >
      {text}
    </button>
  );
};

export default LoginBtn;
