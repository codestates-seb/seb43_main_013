import { MagnifyingGlassIcon } from "@/components/HeaderIcon";

/** 2023/05/10 - 검색창 div - by Kadesti */
const SearchDiv = ({ setInputModal }: { setInputModal: React.Dispatch<boolean> }) => {
  return (
    <div
      className="bg-slate-400 w-full flex justify-end mr-10 rounded-xl h-10 items-center px-3 cursor-text"
      onClick={() => setInputModal(true)}
    >
      <MagnifyingGlassIcon className="w-6 text-white" />
    </div>
  );
};

export default SearchDiv;
