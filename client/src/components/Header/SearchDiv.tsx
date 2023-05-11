import { MagnifyingGlassIcon } from "@/components/HeaderIcon";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = () => {
  return (
    <div className="bg-slate-400 w-full flex justify-end mr-10 rounded-xl h-10 items-center px-3 cursor-text">
      <MagnifyingGlassIcon className="w-6 text-white" />
    </div>
  );
};

export default SearchDiv;
