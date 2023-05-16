import SideCategories from "@/components/SideCategories";
import SortPosts from "@/components/SortPosts";
import ContentItem from "../feedback/ContentItem";
import FeedbackCategories from "./FeedbackCategories";

const categoryDummyData = ["전체", "먹방", "게임", "스포츠", "이슈", "음악", "뷰티", "영화", "쿠킹", "동물", "IT"];
const feedbackCateogoryName = ["영상", "채널", "썸네일"];

const contentItemDummyData1 = {
  feedbackCateogoryName: 1,
  title: " 차냥해 갓상은!",
  content: `인생은 갓상은처럼 인생은 갓형욱처럼 인생은 나처럼 인생은 갓상은처럼 인생은 갓형욱처럼 인생은 나처럼인생은 갓상은처럼 인생은 갓형욱처럼 인생은 나처럼인생은 갓상은처럼 인생은 갓형욱처럼 인생은 나처럼인생은 갓상은처럼 인생은 갓형욱처럼 인생은 나처럼`,
  commentCount: 999,
  viewCount: 999,
  likeCount: 999,
  tag: ["먹방", "코딩", "IT"],
  category: "",
  createdAt: new Date(),
  nickname: "인생은갓상은처럼",
};

const contentItemDummyData2 = {
  feedbackCateogoryName: 1,
  title: " 차냥해 갓상은!",
  content: `인생은 갓상은처럼 인생은 갓형욱처럼 인생은 나처럼 `,
  commentCount: 999,
  viewCount: 999,
  likeCount: 999,
  tag: ["먹방", "코딩", "IT"],
  category: "",
  createdAt: new Date(),
  nickname: "인생은갓상은처럼",
};

/** 2023/05/08 - 자유게시판 메인 화면 - by leekoby */
const FeedbackMain = () => {
  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">🔥 피드백 게시판 🔥</h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* category  */}
          <SideCategories categoryData={categoryDummyData} />
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-between my-1">
            <div className="flex text-3xl font-bold text-left ">
              [영상, 썸네일, ...]
              {/* <FeedbackCategories /> */}
            </div>

            <div className="">
              <SortPosts />
            </div>
          </div>

          {/* post item */}
          <div className="flex flex-col flex-wrap w-full md:flex-row">
            <ContentItem {...contentItemDummyData1} />
            <ContentItem {...contentItemDummyData2} />
            <ContentItem {...contentItemDummyData1} />
            <ContentItem {...contentItemDummyData2} />
          </div>

          <div className="flex flex-col items-center m-auto">무한스크롤</div>
        </section>
        {/* 🔺🔻 버튼? */}
      </div>
    </div>
  );
};

export default FeedbackMain;
