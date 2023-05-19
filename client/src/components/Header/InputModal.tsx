import { useEffect, useRef } from "react";
import ListComp from "./ListComp";

/** 2023/05/10 - 입력 모달창 - by Kadesti */
const InputModal = ({ setInputModal }: { setInputModal: React.Dispatch<boolean> }) => {
  const modalRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      // if (e.target instanceof HTMLDivElement) return;
      if (!modalRef.current) return;
      if (modalRef.current.contains(e.target)) return;

      // 모달을 닫는 함수
      setInputModal(false);
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, [setInputModal]);

  return (
    <>
      {/* <div className="absolute z-10 bg-black/20 w-10 h-screen flex justify-center items-center" /> */}
      {/* <div className="absolute z-10 bg-black/20 w-screen h-screen"> */}
      <div
        className="inset-0 fixed z-10 bg-black/20 w-screen h-screen flex flex-col justify-center items-center"
        onClick={() => {
          setInputModal(false);
        }}
      >
        {/* <div className="absolute z-10 bg-white w-4/5 h-3/4 rounded-2xl flex flex-col items-center top-28 p-8"> */}
        <div className="inset-0 z-10 bg-main-400 w-4/5 h-3/4 rounded-2xl top-28 p-8" ref={modalRef}>
          {/* <input className="w-2/3 bg-slate-400 rounded-xl h-12 px-6 text-white text-xl outline-none" /> */}
          {/* <div className="w-full h-full flex">
            <ListComp label="주간 인기 검색어" />
            <ListComp label="일간 인기 검색어" />
          </div> */}
        </div>
      </div>
    </>
  );
};

export default InputModal;
