import ReactPaginate from "react-paginate";
import { ChevronRightIcon as Right, ChevronLeftIcon as Left } from "@heroicons/react/24/outline";

interface PagenationProps {
  totalPages: number;
  page: number;
  onPageChange: (selected: number) => void;
}

const Pagination = ({ totalPages, page, onPageChange }: PagenationProps) => {
  const handlePageClick = (selectedItem: { selected: number }) => {
    onPageChange(selectedItem.selected + 1);
    window.scrollTo(0, 0);
  };
  return (
    <div>
      <ReactPaginate
        breakLabel={<span>...</span>}
        nextLabel={
          <span className="w-7 h-7 p-1 flex items-center justify-center duration-200 bg-sub-100 rounded-md  active:bg-main-500 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50">
            <Right />
          </span>
        }
        onPageChange={handlePageClick}
        pageRangeDisplayed={3}
        forcePage={page - 1}
        marginPagesDisplayed={1}
        pageCount={totalPages}
        previousLabel={
          <span className="w-7 h-7 p-1 flex items-center justify-center duration-200 bg-sub-100 rounded-md active:bg-main-500 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50">
            <Left />
          </span>
        }
        renderOnZeroPageCount={null}
        containerClassName="flex items-center justify-center mt-8 mb-4 gap-x-4"
        pageClassName="block border-solid border-sub-100 p-1 w-7 h-7 flex items-center justify-center active:bg-main-500 hover:bg-main-400  hover:text-white transtition hover:shadow-md hover:shadow-sub-500/50  rounded-md"
        activeClassName="bg-main-400 p-1 text-white"
      />
    </div>
  );
};

export default Pagination;
