// import useOutsideClick from "./useOutsideClick";

import Link from "next/link";

/** 2023/05/10 - 닉네임 클릭 후 등장 모달창 - by Kadesti */
const NickModal = ({ setNickModal }: { setNickModal: React.Dispatch<boolean> }) => {
  // const outsideRef = useOutsideClick(() => setNickModal(false));

  return (
    <ul className="w-full p-3 bg-slate-400 text-white absolute mt-10 z-10 rounded-xl flex flex-col">
      <Link href="/member" className="cursor-pointer hover:text-slate-200 text-xl mb-2">
        내 정보
      </Link>
      <Link href="/free/write" className="cursor-pointer hover:text-slate-200 text-xl mb-2">
        글쓰기
      </Link>
      <li
        onClick={() => localStorage.removeItem("accessToken")}
        className="cursor-pointer hover:text-slate-200 text-xl mb-2"
      >
        로그아웃
      </li>
    </ul>
  );
};

export default NickModal;
