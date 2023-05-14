import { ResponseCategoriesType } from "@/types/api";

interface SideCategoriesProps {
  categoryData: ResponseCategoriesType[];
  setSelectedCategory: (categoryName: string) => void;
}

/** 2023/05/09 - 게시판 공통 카테고리 - by leekoby */
const SideCategories: React.FC<SideCategoriesProps> = ({ categoryData, setSelectedCategory }) => {
  const categoryClickHandler = (categoryName: string) => {
    setSelectedCategory(categoryName);
  };

  return (
    /* category Container */
    <div className="flex  flex-col  shadow-md my-2.5 rounded-xl w-[50%] md:w-full ">
      {/* category header */}
      <h2 className="flex ml-5 text-xl font-bold text-black mt-7">카테고리 🦝</h2>
      {/* category Item  */}
      <div className="m-5 ">
        {categoryData &&
          categoryData.map((item) => (
            <li className="list-none">
              <button
                type="button"
                key={item.categoryId}
                className="w-full px-5 text-sm leading-10 duration-200 bg-sub-100 rounded hover:bg-main-400 active:bg-main-500 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50"
                onClick={() => {
                  categoryClickHandler(item.categoryName);
                }}
              >
                {item.categoryName}
              </button>
            </li>
          ))}
      </div>
    </div>
  );
};

export default SideCategories;
