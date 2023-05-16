// import useOutsideClick from "./useOutsideClick";

/** 2023/05/10 - 닉네임 클릭 후 등장 모달창 - by Kadesti */
const NickModal = ({ setNickModal }: { setNickModal: React.Dispatch<boolean> }) => {
  // const outsideRef = useOutsideClick(() => setNickModal(false));

  return (
    <div className="w-full p-3 bg-slate-400 text-white absolute mt-10 z-10 rounded-xl">
      {/* <div className="w-full p-3 bg-slate-400 text-white absolute mt-10 z-10 rounded-xl" ref={outsideRef}> */}
      {["내 정보", "글쓰기", "로그아웃"].map((text, i) => (
        <div className="cursor-pointer hover:text-slate-200 text-xl mb-2" key={i}>
          {text}
        </div>
      ))}
    </div>
  );
};

export default NickModal;
