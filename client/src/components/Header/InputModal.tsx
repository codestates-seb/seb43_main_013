import { useEffect, useRef, useState } from "react";
// import currentRank from "../Navbar/currentRank";
import { MagnifyingGlassIcon } from "../HeaderIcon";
import axios from "axios";
import ListComp from "./ListComp";

const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

/** 2023/05/10 - 입력 모달창 - by Kadesti */
const InputModal = ({ setInputModal }: { setInputModal: React.Dispatch<boolean> }) => {
  const modalRef = useRef<HTMLDivElement>(null);
  const [value, setValue] = useState("");

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

  const searchSubmit = async () => {
    const searchResult = await axios.get(`${baseUrl}/api/search?keyword=${value}`);
    return searchResult;
  };
  return (
    <>
      <div
        className="inset-0 fixed z-10 bg-black/20 w-screen h-screen flex flex-col justify-center items-center"
        onClick={() => {
          setInputModal(false);
        }}
      >
        {" "}
        <div
          className="inset-0 z-10 bg-white w-4/5 h-3/4 rounded-2xl top-28 p-8 flex flex-col items-center cursor-default overflow-hidden"
          ref={modalRef}
        >
          <form
            className="w-4/5 p-2 bg-main-500 rounded-xl h-12 text-xl flex justify-center "
            onSubmit={(e) => {
              e.preventDefault();
              searchSubmit();
              setValue("");
            }}
          >
            <input
              className="w-full outline-none mr-2 bg-main-500 border-main-300 border-b-2 text-white px-3"
              value={value}
              onChange={(e) => setValue(e.target.value)}
            />

            <MagnifyingGlassIcon className="w-6 text-white" />
          </form>
          <div className="w-full h-full flex pt-6">
            <ListComp label="최근 검색어" data={[""]} />
            {/* <ListComp label="인기 검색어" data={curRank} /> */}
          </div>
        </div>
      </div>
    </>
  );
};

export default InputModal;
