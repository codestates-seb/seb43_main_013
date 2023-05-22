import LoginWindow from "./Container/LoginWindow";

/** 2023/05/10 - 최상단에 띄울 Modal 컴포넌트 - by Kadesti */
const LoginModal = () => {
  return (
    <>
      <div id="backdrop-root" className="w-screen h-screen absolute bg-black/20 z-10" />
      <div id="modal-root" className="w-screen h-screen absolute flex justify-center items-center z-20">
        <LoginWindow />
      </div>
    </>
  );
};

export default LoginModal;
