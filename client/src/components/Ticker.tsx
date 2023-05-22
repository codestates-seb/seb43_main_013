import { useRef, useEffect } from "react";
import Link from "next/link";

// type
interface Props {
  lists: string[];
}

/** 2023/05/20 - ticker 컴포넌트 - by 1-blue */
const Ticker: React.FC<Props> = ({ lists }) => {
  const wrapperRef = useRef<HTMLUListElement>(null);
  const timerId = useRef<NodeJS.Timer | null>(null);

  useEffect(() => {
    if (timerId.current) return;

    const { current: $ul } = wrapperRef;
    if (!$ul) return;

    timerId.current = setInterval(() => {
      $ul.style.transitionDuration = "600ms";
      $ul.style.marginTop = "-26px";

      window.setTimeout(() => {
        if (!$ul.firstElementChild) return;

        $ul.style.transitionDuration = "";
        $ul.style.marginTop = "";

        $ul.appendChild($ul.firstElementChild);
      }, 600);
    }, 2000);

    return () => {
      timerId.current && clearInterval(timerId.current);
      timerId.current = null;
    };
  }, []);

  return (
    <section className="overflow-hidden h-7">
      <ul ref={wrapperRef} className="flex flex-col">
        {lists.map((list, i) => (
          <Link href={`/search/${decodeURI(list)}`} key={list} className="flex items-center h-7 space-x-2">
            <span className="font-semibold">{i + 1}.</span>
            <span className="font-semibold">{list}</span>
          </Link>
        ))}
      </ul>
    </section>
  );
};

export default Ticker;
