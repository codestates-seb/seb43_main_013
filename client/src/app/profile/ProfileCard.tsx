"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { FilmIcon as OFilmIcon, PencilSquareIcon as OPencilSquareIcon } from "@heroicons/react/24/outline";
import { notFound } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";

// api
import { apiCreateFollow, apiDeleteFollow } from "@/apis";

// store
import { useMemberStore } from "@/store/useMemberStore";

// hook
import { useFetchMember } from "@/hooks/query";
import useCustomToast from "@/hooks/useCustomToast";

// component
import Avatar from "@/components/Avatar";
import Skeleton from "@/components/Skeleton";
import FollowerModal from "./FollowerModal";
import FollowingModal from "./FollowingModal";

interface Props {
  memberId: number;
}

/** 2023/05/14 - 프로필 카드 컴포넌트 - by 1-blue */
const ProfileCard: React.FC<Props> = ({ memberId }) => {
  const { data, isLoading } = useFetchMember({ memberId });
  const toast = useCustomToast();
  const { member } = useMemberStore();
  const queryClient = useQueryClient();
  const [isShowFollowers, setIsShowFollowers] = useState(false);
  const [isShowFollowings, setIsShowFollowings] = useState(false);

  /** 2023/05/18 - 모달 열려있다면 스크롤 금지 - by 1-blue */
  useEffect(() => {
    // 모달이 열려있다면
    if (isShowFollowers || isShowFollowings) {
      document.body.style.overflow = "hidden";
    }
    // 모달이 닫혀있다면
    else {
      document.body.style.overflow = "auto";
    }
  }, [isShowFollowers, isShowFollowings]);

  // 스켈레톤 UI
  if (isLoading) return <Skeleton.ProfileCard />;
  if (!data) return notFound();

  const onClickFollow = async () => {
    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    try {
      // 언팔로우
      if (data.followed) {
        await apiDeleteFollow({ memberId });
        toast({ title: `${data.nickname}님을 언팔로우했습니다.`, status: "success" });
      }
      // 팔로우
      else {
        await apiCreateFollow({ memberId });
        toast({ title: `${data.nickname}님을 팔로우했습니다.`, status: "success" });
      }

      queryClient.invalidateQueries(["member", memberId]);
    } catch (error) {
      console.error(error);

      toast({ title: "팔로우에 실패했습니다.", status: "error" });
    }
  };

  return (
    <>
      <div className="p-8 space-y-4 bg-white shadow-2xl m-4 rounded-lg flex flex-col items-center lg:w-[320px]">
        <Avatar src={data.profileImageUrl} alt="유저의 프로필 이미지" className="w-24 h-24 rounded-full shadow-md" />

        <div className="flex flex-col items-center space-y-1">
          {/* 이름 */}
          <h3 className="font-bold">{data.nickname}</h3>

          {data.memberId === member?.memberId && (
            <>
              <span className="text-sm">{data.email}</span>
              <span className="text-sm">{data.phone}</span>
            </>
          )}
        </div>

        <div className="space-x-6 text-sm">
          <button
            type="button"
            className="px-2 py-1 text-main-400 font-bold border-2 border-main-400 rounded-md transition-colors hover:text-white hover:bg-main-400"
            onClick={() => {
              if (data.followingCount === 0) return toast({ title: "팔로잉이 없습니다", status: "info" });

              setIsShowFollowings(true);
            }}
          >
            팔로잉 {data.followingCount}명
          </button>
          <button
            type="button"
            className="px-2 py-1 text-main-400 font-bold border-2 border-main-400 rounded-md transition-colors hover:text-white hover:bg-main-400"
            onClick={() => {
              if (data.followerCount === 0) return toast({ title: "팔로워가 없습니다", status: "info" });

              setIsShowFollowers(true);
            }}
          >
            팔로워 {data.followerCount}명
          </button>
        </div>

        {/* 자기소개 */}
        <p className="pb-4">{data.introduction}</p>

        {/* 유튜브 링크 */}
        <div className="flex w-full items-center">
          {data.link && (
            <Link href={data.link} target="_blank" referrerPolicy="no-referrer">
              <OFilmIcon className="w-6 h-6 hover:stroke-2 transition-all" />
            </Link>
          )}

          {data.memberId === member?.memberId ? (
            <Link href="/profile/edit" className="ml-auto hover:underline-offset-4 hover:underline">
              <OPencilSquareIcon className="w-6 h-6 hover:stroke-2 transition-all" />
            </Link>
          ) : (
            <button
              type="button"
              onClick={onClickFollow}
              className="m-auto px-2 py-1 text-main-400 font-bold text-xs border-2 border-main-400 rounded-md transition-colors hover:text-white hover:bg-main-400"
            >
              {data.followed ? "언팔로우" : "팔로우"}
            </button>
          )}
        </div>
      </div>

      {/* 팔로워/팔로잉 모달 */}
      {isShowFollowers && <FollowerModal memberId={memberId} onCloseModal={() => setIsShowFollowers(false)} />}
      {isShowFollowings && <FollowingModal memberId={memberId} onCloseModal={() => setIsShowFollowings(false)} />}
    </>
  );
};

export default ProfileCard;
