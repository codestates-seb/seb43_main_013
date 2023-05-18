// component
import ProfileCard from "../../ProfileCard";
import ProfileEditForm from "./ProfileEditForm";

// type
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "CC | 유저 정보 수정",
};

// type
interface Props {
  params: { memberId: string };
}

const Page = ({ params: { memberId } }: Props) => {
  return (
    <article className="flex flex-col lg:flex-row">
      <section className="flex flex-col z-[1]">
        <div className="lg:sticky lg:top-4">
          <ProfileCard memberId={+memberId} />
        </div>
      </section>

      {/* 유저 프로필 수정 폼 */}
      <ProfileEditForm memberId={+memberId} />
    </article>
  );
};

export default Page;
