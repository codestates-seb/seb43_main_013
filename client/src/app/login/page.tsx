import useInputValid from "../../hooks/useInputValid";
import { Input, OAuthCon, SmallBtn } from "../../components/login";

/** 2023/05/05 - 로그인 페이지 컴포넌트 - by Kadesti */
const Login = () => {
  const { idValid, pwValid } = useInputValid();

  return (
    <div className="w-screen flex justify-center my-16 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6  rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">로그인</h1>
        <form className="w-full">
          <Input label="아이디" valid={idValid} />
          <Input label="비밀번호" valid={pwValid} />
        </form>
        <SmallBtn />
        <OAuthCon />
      </div>
    </div>
  );
};

export default Login;
