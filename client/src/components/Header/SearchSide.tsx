import Link from "next/link";
import Headertype from "./Headertype";

const links = [
  { path: "/", name: "커뮤니티" },
  { path: "/notice", name: "공지" },
];

/** 2023/05/10 - 커뮤니티 트렌드 라우팅 텍스트 - by Kadesti */
const SearchSide = ({ array }: { array: Headertype[] }) => {
  return (
    <div className="space-x-6">
      {links.map((link, idx) => (
        <Link key={idx} href={link.path} className="text-2xl break-keep cursor-pointer hover:text-main-400">
          {link.name}
        </Link>
      ))}
    </div>
  );
};

export default SearchSide;
