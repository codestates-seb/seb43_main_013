"use client";
import { MustInput, OptionalInput } from "./SignupInput";
// import ProfileInput from "./ProfileInput";
import SignupBind from "./SignupBind";

const SignupForm = () => {
  const { mustBind, optionBind, profileImageUrl, setProfileImageUrl, onsubmit, isSubmit } = SignupBind();
  const disabled = mustBind.findIndex((el) => el.valid === false) !== -1;

  return (
    <form
      className="w-full flex flex-col items-center"
      onSubmit={(e) => {
        e.preventDefault();

        onsubmit();
      }}
    >
      {mustBind.map((value, idx) => {
        return <MustInput mustBind={value} isSubmit={isSubmit} key={idx} />;
      })}
      {optionBind.map((value, idx) => {
        return <OptionalInput optionBind={value} key={idx} />;
      })}

      {/* <ProfileInput profile={profileImageUrl} setProfile={setProfileImageUrl} /> */}
      <button
        className={`w-4/5 h-16 mt-5 mb-6 flex justify-center items-center text-3xl rounded-2xl  ${
          disabled ? "bg-green-200 text-stone-400" : "bg-green-400 hover:bg-green-200 hover:text-slate-400"
        }`}
        type="submit"
        disabled={disabled}
      >
        회원가입
      </button>
    </form>
  );
};

export default SignupForm;
