import { useFeedbackCategoriesStore } from "@/store/useFeedbackCategoriesStore";
import { ResponseFeedbackCategoriesType } from "@/types/api";
import { useState } from "react";

interface feedbackCategoriesProps {
  feedbackCategoryData: ResponseFeedbackCategoriesType[];
  selectedFeedbackCategory?: {
    feedbackCategoryName: string;
    feedbackCategoryId: number;
  };
}
/** 2023/05/15- 피드백 게시판용 카테고리 - by leekoby */
const FeedbackCategories: React.FC<feedbackCategoriesProps> = ({ feedbackCategoryData }) => {
  const selectedFeedbackCategory = useFeedbackCategoriesStore((state) => state.selectedFeedbackCategory);
  const setFeedbackSelectedCategory = useFeedbackCategoriesStore((state) => state.setSelectedFeedbackCategory);

  const categoryClickHandler = (feedbackCategory: ResponseFeedbackCategoriesType) => {
    setFeedbackSelectedCategory(feedbackCategory.feedbackCategoryName, feedbackCategory.feedbackCategoryId);
  };

  // 작은 사이즈용
  const [isShown, setIsShown] = useState(true);
  // 작은 사이즈용
  const toggleSideCategories = () => {
    setIsShown(!isShown);
  };

  return (
    /* category Container */
    <div className="flex rounded-xl gap-2">
      {/* category Item  */}
      {feedbackCategoryData?.map((feedbackCategory) => (
        <li className="list-none">
          <button
            type="button"
            key={feedbackCategory.feedbackCategoryId}
            className={`w-full px-5 text-sm leading-10 duration-200 rounded hover:bg-main-400 active:bg-main-500 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50
            ${
              selectedFeedbackCategory?.feedbackCategoryId === feedbackCategory.feedbackCategoryId
                ? "bg-main-500 text-white shadow-lg shadow-sub-500/50"
                : "bg-sub-100 "
            }`}
            onClick={() => {
              categoryClickHandler(feedbackCategory);
            }}
          >
            {feedbackCategory.feedbackCategoryName}
          </button>
        </li>
      ))}
    </div>
  );
};
export default FeedbackCategories;
