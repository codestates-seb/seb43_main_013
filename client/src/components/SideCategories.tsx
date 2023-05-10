"use client";

interface SideCategoriesProps {
  categoryData: string[];
}

/** 2023/05/09 - 게시판 공통 카테고리 - by leekoby */
const SideCategories: React.FC<SideCategoriesProps> = ({ categoryData }) => {
  const categoryClickHandler: (item: string) => void = (item) => {
    console.log(item);
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
            <div
              key={item}
              className="px-5 text-sm leading-10 duration-200 bg-gray-100 rounded w-53 hover:bg-main-400 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-gray-500/50"
              onClick={() => {
                categoryClickHandler(item);
              }}
            >
              {item}
            </div>
          ))}
      </div>
    </div>
  );
};

export default SideCategories;
