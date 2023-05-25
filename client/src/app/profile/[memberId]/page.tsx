// component
import { apiSSRFetchMember } from "@/apis/ssr";
import ProfileBookmarkedBoardList from "../ProfileBookmarkedBoardList";
import ProfileCard from "../ProfileCard";
import ProfileLikedBoardList from "../ProfileLikedBoardList";
import ProfileWrittenBoardList from "../ProfileWrittenBoardList";
import ProfileNav from "./ProfileNav";
import { Metadata } from "next";
import { getMetadata } from "@/libs";

// type
interface Props {
  params: { memberId: string };
  searchParams: { type: "written" | "bookmarked" | "liked"; page: string };
}

/** 2023/05/25 - 메타데이터 - by 1-blue */
export const generateMetadata = async ({ params: { memberId } }: Props): Promise<Metadata> => {
  const initialData = await apiSSRFetchMember({ memberId: +memberId });

  return getMetadata({
    title: initialData.nickname,
    description: `${initialData.nickname}님의 프로필 페이지입니다.` + "\n" + initialData.introduction,
    images: initialData.profileImageUrl ? [initialData.profileImageUrl] : undefined,
  });
};

const Page = async ({ params: { memberId }, searchParams: { type = "written", page = "1" } }: Props) => {
  const initialData = await apiSSRFetchMember({ memberId: +memberId });

  return (
    <article className="relative flex flex-col lg:flex-row">
      {/* 유저 프로필 / 프로필 네비게이션 */}
      <section className="flex flex-col z-[1]">
        <div className="lg:sticky lg:top-4">
          <ProfileCard memberId={+memberId} initialData={initialData} />
          <ProfileNav type={type} memberId={+memberId} />
        </div>
      </section>

      {/* 내 게시글들 / 북마크 누른 게시글들 / 좋아요 누른 게시글들 */}
      <section className="flex-1 p-8 space-y-4 bg-white shadow-black/40 shadow-sm m-4 rounded-lg flex flex-col items-center lg:self-start">
        {type === "written" && <ProfileWrittenBoardList memberId={+memberId} page={+page} />}
        {type === "bookmarked" && <ProfileBookmarkedBoardList memberId={+memberId} page={+page} />}
        {type === "liked" && <ProfileLikedBoardList memberId={+memberId} page={+page} />}
      </section>
    </article>
  );
};

export default Page;
