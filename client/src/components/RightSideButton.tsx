import { ChevronDoubleUpIcon as Up, ChevronDoubleDownIcon as Down } from "@heroicons/react/24/outline";
import { PencilIcon } from "@heroicons/react/24/solid";
import Link from "next/link";
import { useEffect, useState } from "react";

interface RightSideButtonProps {
  destination: string;
}

const RightSideButton: React.FC<RightSideButtonProps> = ({ destination }) => {
  const [isClient, setIsClient] = useState(false);

  useEffect(() => {
    setIsClient(true);
  }, []);

  const scrollPage = (direction: "top" | "bottom") => {
    if (!isClient) return;

    const { scrollHeight, scrollTop } = document.documentElement;
    const offset = direction === "top" ? 0 : scrollHeight;
    window.scroll({ top: offset, left: 0, behavior: "smooth" });
  };

  return (
    <div className="fixed right-0 top-1/2 xl:right-6 2xl:right-20 transform -translate-y-1/2 ml-2">
      <div className="flex flex-col items-center justify-center gap-y-2">
        <span
          onClick={() => scrollPage("top")}
          className="w-6 h-6 flex items-center justify-center duration-200 bg-sub-100 rounded-md active:bg-main-500 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50"
        >
          <Up />
        </span>
        <Link href={destination}>
          <button
            type="button"
            className="w-11 h-11 p-2 bg-main-300 text-sub-700 rounded-full hover:bg-main-500 active:bg-main-600 focus:outline-none focus:bg-main-500 focus:ring-2 focus:ring-main-500 focus:ring-offset-2 duration-200 hover:scale-110 transtition hover:shadow-md hover:shadow-sub-500/50 focus:text-white hover:text-white"
          >
            <PencilIcon />
          </button>
        </Link>
        <span
          onClick={() => scrollPage("bottom")}
          className="w-6 h-6 flex items-center justify-center duration-200 bg-sub-100 rounded-md active:bg-main-500 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50"
        >
          <Down />
        </span>
      </div>
    </div>
  );
};

export default RightSideButton;
