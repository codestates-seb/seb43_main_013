import Link from "next/link";
import Headertype from "./Headertype";

/** 2023/05/10 - 커뮤니티 트렌드 라우팅 텍스트 - by Kadesti */
const SearchSide = ({ array }: { array: Headertype[] }) => {
  return (
    <div className="">
      {array.map((el, idx) => {
        return (
          <Link
            href={el.path}
            className={`text-2xl break-keep cursor-pointer text-black hover:text-main-400 ${el.style}`}
            key={idx}
          >
            {el.name}
          </Link>
        );
      })}
    </div>
  );
};

export default SearchSide;
