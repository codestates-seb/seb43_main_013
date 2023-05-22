import LoginBtn from "../Button/LoginBtn";

/** 2023/05/10 - 로그인 버튼 컨테이너  - by Kadesti */
const OAuthCon = () => {
  // const btnText: string[] = ["OAuth", "OAuth"];
  const btnText: string[] = new Array(2).fill("OAuth");

  return (
    <>
      {btnText.map((text, idx) => (
        <LoginBtn text={text} key={idx} />
      ))}
      <button className="bg-green-400 w-full h-16 flex justify-center items-center text-3xl rounded-2xl cursor-pointer hover:bg-green-200 hover:text-slate-400">
        OAuth
      </button>
    </>
  );
};

export default OAuthCon;
