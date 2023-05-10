import SideCategories from "@/components/BoardMain/SideCategories";
import SortPosts from "@/components/BoardMain/SortPosts";
import ContentItem from "./ContentItem";
import PopularPosts from "./PopularPosts";

/** 2023/05/09 - 자유게시판 메인 화면테스트용 더미데이터 - by leekoby */
const categoryDummyData = ["전체", "먹방", "게임", "스포츠", "이슈", "음악", "뷰티", "영화", "쿠킹", "동물", "IT"];

/** 2023/05/09 - 자유게시판 메인 화면테스트용 더미데이터 - by leekoby */
const contentItemDummyData1 = {
  freeboardID: 1,
  title: " 차냥해 갓상은!",
  content: "인생은 나처럼",
  commentCount: 999,
  viewCount: 999,
  likeCount: 999,
  tag: ["먹방", "코딩", "IT"],
  category: "",
  createdAt: new Date(),
  nickname: "인생은갓상은처럼",
};

/** 2023/05/09 - 자유게시판 메인 화면테스트용 더미데이터 - by leekoby */
const contentItemDummyData2 = {
  freeboardID: 1,
  title: " 차냥해 갓상은!",
  content:
    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit saepe eaque quam repudiandae facere sit tenetur nesciunt, voluptatum harum iure minima quasi quos inventore perferendis voluptatem expedita eius aspernatur deleniti placeat consectetur corporis impedit delectus consequuntur laboriosam. Cum voluptatibus officia minima eveniet harum quidem ullam est provident magni. Ea dicta porro aspernatur illum. Dolorem itaque laudantium voluptas commodi soluta amet!",
  commentCount: 999,
  viewCount: 999,
  likeCount: 999,
  tag: ["먹방", "코딩", "IT"],
  category: "",
  createdAt: new Date(),
  nickname: "인생은갓상은처럼",
};

/** 2023/05/08 - 자유게시판 메인 화면 - by leekoby */
const FreeMain = () => {
  return (
    //  전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left">🔥 자유게시판 🔥</h1>
      <div className="flex flex-col md:flex-row ">
        {/* Left Side */}
        <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
          {/* category  */}
          <SideCategories categoryData={categoryDummyData} />
          <PopularPosts />
        </aside>
        {/* rightside freeboard post list */}
        <section className="flex flex-col md:w-0 ml-5  grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-between">
            <h1 className="py-1 text-3xl font-bold text-left"> 전체 </h1>
            <div className="self-end px-2 py-1 text-gray-600 bg-white border border-gray-600 rounded-lg cursor-pointer outline outline-2 outline-gray-600">
              <SortPosts />
            </div>
          </div>

          {/* post item */}
          <ContentItem {...contentItemDummyData1} />
          <ContentItem {...contentItemDummyData2} />

          <div className="flex flex-col items-center m-auto">페이지네이션</div>
        </section>
        {/* 🔺🔻 버튼? */}
      </div>
    </div>
  );
};

export default FreeMain;
