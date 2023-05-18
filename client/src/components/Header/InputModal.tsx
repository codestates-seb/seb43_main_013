import ListComp from "./ListComp";

/** 2023/05/10 - 입력 모달창 - by Kadesti */
const InputModal = ({ setInputModal }: { setInputModal: React.Dispatch<boolean> }) => {
  return (
    <>
      {/* <div className="absolute z-10 bg-black/20 w-10 h-screen flex justify-center items-center" /> */}
      {/* <div className="absolute z-10 bg-black/20 w-screen h-screen"> */}
      <div
        className="fixed z-10 bg-black/20 w-screen h-screen"
        onClick={() => {
          setInputModal(false);
        }}
      />
      {/* <div className="absolute z-10 bg-white w-4/5 h-3/4 rounded-2xl flex flex-col items-center top-28 p-8"> */}
      <div className="absolute z-10 bg-main-400 w-4/5 h-3/4 rounded-2xl flex flex-col items-center top-28 p-8">
        {/* <input className="w-2/3 bg-slate-400 rounded-xl h-12 px-6 text-white text-xl outline-none" /> */}
        {/* <div className="w-full h-full flex">
            <ListComp label="주간 인기 검색어" />
            <ListComp label="일간 인기 검색어" />
          </div> */}
      </div>
    </>
  );
};

export default InputModal;
