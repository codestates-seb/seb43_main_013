// import useInputValid from "@/hooks/useInputValid";
import SmallBtn from "../Button/SmallBtn";
import LoginBtn from "../Button/LoginBtn";
import OAuthCon from "./OAuthCon";
import LoginInput from "../LoginInput";

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const LoginWindow = () => {
  // const { idValid, pwValid } = useInputValid();

  return (
    <div className=" w-screen flex justify-center items-center">
      <div className=" bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">로그인</h1>
        <form className="w-full">
          {/* <LoginInput label="아이디" valid={idValid} />
          <LoginInput label="비밀번호" valid={pwValid} /> */}
          {/* <LoginInput label="아이디" />
          <LoginInput label="비밀번호" /> */}
          <SmallBtn />
          <LoginBtn text="로그인" />
          <OAuthCon />
        </form>
      </div>
    </div>
  );
};

export default LoginWindow;
