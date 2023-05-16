// component
import ProfileCard from "../ProfileCard";
import ProfileEditForm from "./ProfileEditForm";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 유저 정보 수정",
};

const Page = () => {
  return (
    <article className="flex flex-col lg:flex-row">
      {/* 유저 프로필 */}
      <ProfileCard />

      {/* 유저 프로필 수정 폼 */}
      <ProfileEditForm />
    </article>
  );
};

export default Page;
