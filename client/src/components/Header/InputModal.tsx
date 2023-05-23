import { FormEventHandler, useEffect, useRef, useState } from "react";
import { MagnifyingGlassIcon } from "../HeaderIcon";
import { XMarkIcon } from "@heroicons/react/24/outline";

import useCustomToast from "@/hooks/useCustomToast";
import { twMerge } from "tailwind-merge";
import KeywordCloud from "../KeywordCloud";
import Link from "next/link";
import { useRouter } from "next/navigation";

/** 2023/05/10 - 입력 모달창 - by Kadesti */
const InputModal = ({ setInputModal }: { setInputModal: React.Dispatch<boolean> }) => {
  const router = useRouter();
  const toast = useCustomToast();
  const modalRef = useRef<HTMLDivElement>(null);
  const formRef = useRef<HTMLFormElement>(null);
  const [value, setValue] = useState("");

  const [isFocus, setIsFocus] = useState(false);
  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (!modalRef.current) return;
      if (modalRef.current.contains(e.target)) return;

      // 모달을 닫는 함수
      setInputModal(false);
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, [setInputModal]);

  const [isShow, setIsShow] = useState(false);
  useEffect(() => {
    const formCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (!formRef.current) return;
      if (formRef.current.contains(e.target)) return;

      // 추천 검색어를 닫는 함수
      setIsShow(false);
    };

    window.addEventListener("click", formCloseHandler);
    return () => window.removeEventListener("click", formCloseHandler);
  }, [setInputModal]);

  const onSearch: FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    if (!value.trim()) return toast({ title: "검색어를 입력해주세요!", status: "warning" });

    const keywords = JSON.parse(localStorage.getItem("keywords") || "[]");

    // 최근 검색어
    if (Array.isArray(keywords)) {
      localStorage.setItem("keywords", JSON.stringify([...new Set([value, ...keywords])]));
    }

    toast({ title: `"${value}"를 검색했습니다.` });

    router.push(`/search?keyword=${value}`);
    setInputModal(false);
  };

  const [recentWords, setRecentWords] = useState<string[]>([]);

  /** 2023/05/22 - 최근 검색어들 가져오기 - by 1-blue */
  useEffect(() => {
    const words = JSON.parse(localStorage.getItem("keywords")!) as string[];

    setRecentWords(words);
  }, []);

  /** 2023/05/22 - 최근 검색어 제거 - by 1-blue */
  const onDeleteRecentWord = (targetWord: string) => {
    const words = JSON.parse(localStorage.getItem("keywords")!) as string[];

    localStorage.setItem("keywords", JSON.stringify(words.filter((word) => word !== targetWord)));

    setRecentWords((prev) => prev.filter((v) => v !== targetWord));

    toast({ title: `"${targetWord}"를 삭제했습니다.`, duration: 500 });
  };

  /** 2023/05/22 - 최근 검색어 필터링 - by 1-blue */
  const onFilterRecentWord = (searchWord: string) => {
    const words = JSON.parse(localStorage.getItem("keywords")!) as string[];

    setRecentWords(words.filter((word) => word.includes(searchWord)));
  };

  return (
    <article
      className="inset-0 fixed z-10 bg-black/80 backdrop-blur-sm w-screen h-screen flex flex-col justify-center items-center animate-fade-in"
      onClick={() => setInputModal(false)}
    >
      <div ref={modalRef} className="space-y-16">
        <form
          ref={formRef}
          onSubmit={onSearch}
          className={twMerge(
            "relative w-[250px] px-2 py-1 flex justify-center bg-white border-[3px] border-main-400 space-x-2 rounded-sm mx-auto transition-colors",
            isFocus && "border-main-500",
          )}
        >
          <input
            type="search"
            value={value}
            onChange={(e) => {
              setValue(e.target.value);
              onFilterRecentWord(e.target.value);
            }}
            className="flex-1 outline-none text-lg"
            onFocus={() => {
              setIsFocus(true);
              setIsShow(true);
            }}
            onBlur={() => setIsFocus(false)}
          />

          <button type="submit">
            <MagnifyingGlassIcon
              className={twMerge("w-6 h-6 text-main-500 stroke-2 transition-colors", isFocus && "text-main-600")}
            />
          </button>

          {isShow && (
            <ul className="absolute top-[41px] right-0 w-full bg-white max-h-[40vh] overflow-y-auto rounded-b-md scrollbar">
              {recentWords.map((recentWord) => (
                <li key={recentWord}>
                  <Link
                    href={`/search?keyword=${recentWord}`}
                    className="flex items-center justify-between text-left px-4 py-2 transition-colors hover:bg-main-100"
                  >
                    <span>{recentWord}</span>
                    <button
                      type="button"
                      onClick={(e) => {
                        e.preventDefault();
                        e.stopPropagation();

                        onDeleteRecentWord(recentWord);
                      }}
                    >
                      <XMarkIcon className="w-6 h-6 text-sub-400 stroke-2" />
                    </button>
                  </Link>
                </li>
              ))}
            </ul>
          )}
        </form>

        {/* 키워드 클라우드 */}
        <section className="w-full flex justify-evenly lg:space-x-20">
          {/* 월간 */}
          <div className="w-[30vw] max-w-[500px] rounded-md hidden lg:inline-block">
            <h4 className="text-3xl my-4 font-bold text-white">월간 인기 검색어</h4>
            <div>
              <KeywordCloud type="monthly" />
            </div>
          </div>
          {/* 주간 */}
          <div className="w-[60vw] lg:w-[30vw] max-w-[500px] rounded-md">
            <h4 className="text-3xl my-4 font-bold text-white">주간 인기 검색어</h4>
            <div>
              <KeywordCloud type="weekly" />
            </div>
          </div>
        </section>
      </div>
    </article>
  );
};

export default InputModal;
