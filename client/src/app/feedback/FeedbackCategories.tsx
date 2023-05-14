"use client";
import { ResponseFeedbackCategoriesType } from "@/types/api";

interface feedbackCategoriesProps {
  feedbackCategoryData: ResponseFeedbackCategoriesType[];
  setSelectedFeedbackCategory: (categoryName: string) => void;
}
/** 2023/05/14 - 피드백 게시판용 카테고리 - by leekoby */
const FeedbackCategories: React.FC<feedbackCategoriesProps> = ({
  feedbackCategoryData,
  setSelectedFeedbackCategory,
}) => {
  const categoryClickHandler = (feedbackCategoryName: string) => {
    setSelectedFeedbackCategory(feedbackCategoryName);
    console.log(feedbackCategoryName);
  };

  return (
    /* category Container */
    <div className="flex  my-2.5 rounded-xl gap-x-2">
      {/* category Item  */}
      {feedbackCategoryData &&
        feedbackCategoryData.map((item) => (
          <li className="list-none">
            <button
              type="button"
              key={item.feedbackCategoryId}
              className="w-full px-5 text-sm leading-10 duration-200 bg-sub-100 rounded hover:bg-main-400 active:bg-main-500 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50"
              onClick={() => {
                categoryClickHandler(item.feedbackCategoryName);
              }}
            >
              {item.feedbackCategoryName}
            </button>
          </li>
        ))}
    </div>
  );
};
export default FeedbackCategories;
