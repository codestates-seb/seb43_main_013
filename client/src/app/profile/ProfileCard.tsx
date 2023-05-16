"use client";

import Link from "next/link";
import { FilmIcon as OFilmIcon, PencilSquareIcon as OPencilSquareIcon } from "@heroicons/react/24/outline";

// hook
import { useFetchMemeber } from "@/hooks/query";

// component
import Avatar from "@/components/Avatar";

/** 2023/05/14 - 프로필 카드 컴포넌트 - by 1-blue */
const ProfileCard = () => {
  const { member, isLoading } = useFetchMemeber({ memberId: 1 });

  // FIXME: 예외 처리
  if (!member) return <>없는 멤버</>;

  return (
    <section className="border-r p-8 space-y-4 bg-white shadow-2xl m-4 rounded-lg flex flex-col items-center lg:max-w-[350px] h-full lg:sticky lg:top-4">
      <Avatar src={member.profileImageUrl} alt="유저의 프로필 이미지" className="w-24 h-24 rounded-full shadow-md" />

      <div className="flex flex-col items-center space-y-1">
        {/* 이름 */}
        <h3 className="font-bold">{member.nickname}</h3>

        {/* FIXME: 본인이라면 이메일과 휴대폰 번호 */}
        {true && (
          <>
            <span className="text-sm">{member.email}</span>
            <span className="text-sm">{member.phone}</span>
          </>
        )}
      </div>

      {/* FIXME: 팔로잉/팔로워 채워넣기 */}
      <div className="space-x-6 text-sm">
        <button
          type="button"
          className="px-2 py-1 text-main-400 font-bold border-2 border-main-400 rounded-md transition-colors hover:text-white hover:bg-main-400"
        >
          팔로잉 {30}명
        </button>
        <button
          type="button"
          className="px-2 py-1 text-main-400 font-bold border-2 border-main-400 rounded-md transition-colors hover:text-white hover:bg-main-400"
        >
          팔로워 {30}명
        </button>
      </div>

      {/* 자기소개 */}
      <p className="pb-4">{member.introduction}</p>

      {/* 유튜브 링크 */}
      <div className="flex w-full items-center">
        {member.link && (
          <Link href={member.link} target="_blank" referrerPolicy="no-referrer">
            <OFilmIcon className="w-6 h-6 hover:stroke-2 transition-all" />
          </Link>
        )}

        {/* FIXME: 내 정보 기준으로 나누기 정보 수정 및 팔로우 버튼 */}
        {true ? (
          <Link href="/profile/edit" className="ml-auto hover:underline-offset-4 hover:underline">
            <OPencilSquareIcon className="w-6 h-6 hover:stroke-2 transition-all" />
          </Link>
        ) : (
          <button
            type="button"
            className="ml-auto px-2 py-1 text-main-400 font-bold text-xs border-2 border-main-400 rounded-md transition-colors hover:text-white hover:bg-main-400"
          >
            {/* FIXME: 팔로우/언팔로우 */}
            팔로우
          </button>
        )}
      </div>
    </section>
  );
};

export default ProfileCard;
