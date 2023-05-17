import { ChevronDoubleUpIcon as Up, ChevronDoubleDownIcon as Down } from "@heroicons/react/24/outline";
import Link from "next/link";

interface RightSideButtonProps {
  destination: string;
}

/** 2023/05/15 - 오른쪽 사이드 상하이동 글쓰기 - by leekoby */
const RightSideButton: React.FC<RightSideButtonProps> = ({ destination }) => {
  /** 2023/05/15 - 오른쪽 사이드 scroll 버튼 함수 - by leekoby */
  const scrollPage = (direction: "top" | "bottom") => {
    const { scrollHeight, scrollTop } = document.documentElement;
    const offset = direction === "top" ? 0 : scrollHeight;
    window.scroll({ top: offset, left: 0, behavior: "smooth" });
  };
  return (
    <>
      <span
        onClick={() => scrollPage("top")}
        className="w-6 h-6 flex items-center justify-center duration-200 bg-sub-100 rounded-md  active:bg-main-500 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50"
      >
        <Up />
      </span>
      <Link
        href={destination}
        className="flex justify-center items-center bg-main-400 text-white my-3 px-3 py-2 rounded-md text-sm font-bold transition-colors hover:bg-main-500 active:bg-main-600  focus:outline-none focus:bg-main-500 focus:ring-2 focus:ring-main-500 focus:ring-offset-2 duration-200 hover:scale-105 transtition  hover:shadow-md hover:shadow-sub-500/50"
      >
        글쓰기
      </Link>
      <span
        onClick={() => scrollPage("bottom")}
        className="w-6 h-6 flex items-center justify-center duration-200 bg-sub-100 rounded-md  active:bg-main-500 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50"
      >
        <Down />
      </span>
    </>
  );
};

export default RightSideButton;
