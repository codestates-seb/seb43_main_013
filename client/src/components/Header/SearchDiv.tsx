import { MagnifyingGlassIcon } from "@/components/HeaderIcon";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = () => {
  return (
    <div className="bg-main-300 w-full flex justify-end mx-10 rounded-xl h-10 items-center px-5 py-1 cursor-text">
      {/* <div className="w-full h-full border-b-2 border-white mr-5" /> */}
      <input className="w-full h-full border-b-2 border-white bg-main-300 mr-5 outline-none" />
      <MagnifyingGlassIcon className="w-6 text-white" />
    </div>
  );
};

export default SearchDiv;
