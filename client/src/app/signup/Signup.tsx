import { disabled, setDisabled } from "./disabledState";
import SignContainer from "./SignContainer";
import LabelData from "./LabelData";

/** 2023/05/05 - 회원가입 페이지 컴포넌트 - by Kadesti */
const Signup = () => {
  console.log("LabelData", LabelData);
  console.log("LabelData Type", typeof LabelData);

  const label = LabelData;
  console.log("label.length: ", label.length);

  return (
    <div className="w-full flex justify-center mt-16 px-auto">
      <div className="bg-white w-2/5 flex flex-col items-center p-6 rounded-xl drop-shadow-xl">
        <h1 className="text-5xl mb-6">회원가입</h1>
        <form className="w-full flex flex-col items-center">
          {/* <SignContainer /> */}
          <div className="w-full h-44 border-2 border-black flex justify-center items-center rounded-lg mt-6 mb-3 text-2xl">
            사진
          </div>
          <button
            disabled={disabled}
            className={`w-4/5 h-16 mt-5 mb-6 flex justify-center items-center text-3xl rounded-2xl ${
              disabled ? "bg-slate-300 text-white" : "bg-green-400 hover:bg-green-200 hover:text-slate-400"
            }`}
          >
            회원가입
          </button>
        </form>
      </div>
    </div>
  );
};

export default Signup;
