import { useState } from "react";
import { ResponseJobCategoriesType } from "@/types/api";
import { useCategoriesStore } from "@/store";

interface JobCategoriessProps {
  jobCategories: ResponseJobCategoriesType[];
  selectedCategory?: {
    categoryName: string;
    categoryId: number;
  };
}

/** 2023/05/09 - 게시판 공통 카테고리 - by leekoby */
const JobCategories: React.FC<JobCategoriessProps> = ({ jobCategories, selectedCategory }) => {
  /** 2023/05/14 -  공통 카테고리 선택 전역 상태 - by leekoby */
  const setSelectedCategory = useCategoriesStore((state) => state.setSelectedCategory);

  const categoryClickHandler = (jobCategories: ResponseJobCategoriesType) => {
    setSelectedCategory(jobCategories.jobCategoryName, jobCategories.jobCategoryId);
  };

  // 작은 사이즈용
  const [isShown, setIsShown] = useState(true);
  // 작은 사이즈용
  const toggleSideCategories = () => {
    setIsShown(!isShown);
  };

  return (
    <div className="flex flex-col shadow-md my-2.5 rounded-xl w-[50%] md:w-full ">
      {/* category header */}
      <h2 className="flex ml-5 text-xl font-bold text-black mt-7">카테고리 🦝</h2>

      {/* category Item  */}
      <div className="m-5  ">
        {jobCategories?.map((category) => (
          <li className="list-none" key={category.jobCategoryId}>
            <button
              type="button"
              className={`w-full px-5 text-sm leading-10 duration-200 rounded hover:bg-main-400 active:bg-main-500 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50
                ${
                  selectedCategory?.categoryId === category.jobCategoryId
                    ? "bg-main-500 text-white shadow-lg shadow-sub-500/50"
                    : "bg-sub-100 "
                }`}
              onClick={() => {
                categoryClickHandler(category);
              }}
            >
              {category.jobCategoryName}
            </button>
          </li>
        ))}
      </div>
    </div>
  );
};
export default JobCategories;
