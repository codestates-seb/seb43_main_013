// component
import ProfileBookmarkedBoardList from "../ProfileBookmarkedBoardList";
import ProfileCard from "../ProfileCard";
import ProfileLikedBoardList from "../ProfileLikedBoardList";
import ProfileWrittenBoardList from "../ProfileWrittenBoardList";
import ProfileNav from "./ProfileNav";

// type
interface Props {
  params: { memberId: string };
  searchParams: { type: "written" | "bookmarked" | "liked"; page: string };
}

const Page = ({ params: { memberId }, searchParams: { type = "written", page = "1" } }: Props) => {
  return (
    <article className="relative flex flex-col lg:flex-row">
      {/* 유저 프로필 / 프로필 네비게이션 */}
      <section className="flex flex-col z-[1]">
        <div className="lg:sticky lg:top-4">
          <ProfileCard memberId={+memberId} />
          <ProfileNav type={type} memberId={+memberId} />
        </div>
      </section>

      {/* 내 게시글들 / 북마크 누른 게시글들 / 좋아요 누른 게시글들 */}
      <section className="flex-1 p-8 space-y-4 bg-white shadow-2xl m-4 rounded-lg flex flex-col items-center lg:self-start">
        {type === "written" && <ProfileWrittenBoardList memberId={+memberId} page={+page} />}
        {type === "bookmarked" && <ProfileBookmarkedBoardList memberId={+memberId} page={+page} />}
        {type === "liked" && <ProfileLikedBoardList memberId={+memberId} page={+page} />}
      </section>
    </article>
  );
};

export default Page;
