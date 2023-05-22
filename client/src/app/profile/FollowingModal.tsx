"use client";

import { useEffect, useRef } from "react";
import Link from "next/link";
import { useQueryClient } from "@tanstack/react-query";
import { BellAlertIcon } from "@heroicons/react/24/outline";

// api
import { apiCreateFollow, apiDeleteFollow } from "@/apis";

// component
import Avatar from "@/components/Avatar";

// hook
import { useFetchFollowings } from "@/hooks/query";
import { useMemberStore } from "@/store/useMemberStore";
import useCustomToast from "@/hooks/useCustomToast";

// component
import InfiniteScrollContainer from "@/components/InfiniteScrollContainer";

interface Props {
  memberId: number;
  nickname: string;
  onCloseModal: () => void;
}

/** 2023/05/18 - 팔로워 모달 컴포넌트 - by 1-blue */
const FollowingModal: React.FC<Props> = ({ memberId, nickname, onCloseModal }) => {
  const { member } = useMemberStore();
  const toast = useCustomToast();
  const { data, fetchNextPage, hasNextPage } = useFetchFollowings({ memberId, page: 1, size: 1 });
  const queryClient = useQueryClient();

  const modalRef = useRef<null | HTMLUListElement>(null);

  /** 2023/05/18 - 외부 클릭 시 모달 닫기 - by 1-blue */
  useEffect(() => {
    const modalCloseHandler = (e: MouseEvent) => {
      if (!(e.target instanceof HTMLElement)) return;
      if (e.target instanceof HTMLButtonElement) return;
      if (e.target instanceof HTMLSpanElement) return;
      if (!modalRef.current) return;
      if (modalRef.current.contains(e.target)) return;

      // 모달을 닫는 함수
      onCloseModal();
    };

    window.addEventListener("click", modalCloseHandler);
    return () => window.removeEventListener("click", modalCloseHandler);
  }, [onCloseModal]);

  /** 2023/05/18 - 팔로우/언팔로우 핸들러 - by 1-blue */
  const onClickFollowOrUnfollow = async (followed: boolean, targetId: number, nickname: string) => {
    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    try {
      // 언팔로우
      if (followed) {
        await apiDeleteFollow({ memberId: targetId });
        toast({ title: `${nickname}님을 언팔로우했습니다.`, status: "success" });
      }
      // 팔로우
      else {
        await apiCreateFollow({ memberId: targetId });
        toast({ title: `${nickname}님을 팔로우했습니다.`, status: "success" });
      }

      queryClient.invalidateQueries(["member", memberId]);
      queryClient.invalidateQueries(["followings"]);
      queryClient.invalidateQueries(["followers"]);
    } catch (error) {
      console.error(error);

      toast({ title: "팔로우에 실패했습니다.", status: "error" });
    }
  };

  return (
    <section className="fixed inset-0 bg-black/70 animate-fade-in">
      <ul
        className="w-[280px] max-h-[60vh] mx-auto mt-[16vh] overflow-y-auto flex flex-col bg-sub-200 rounded-md scrollbar"
        ref={modalRef}
      >
        <li className="py-4 bg-main-200 text-center font-bold space-y-2">
          <h5 className="text-xl">{nickname}님의 팔로잉들</h5>
          <div className="border-[3px] border-dotted inline-block rounded-full p-2 border-main-600">
            <BellAlertIcon className="w-10 h-10 text-main-600" />
          </div>
        </li>
        <InfiniteScrollContainer fetchMore={fetchNextPage} hasMore={hasNextPage}>
          {data?.pages.map((page) =>
            page.data.map((following) => (
              <li
                key={following.memberId}
                className="px-4 py-2 flex items-center border-b border-sub-400 last-of-type:border-b-0 odd:bg-main-100"
              >
                <Avatar
                  src={following.profileImageUrl}
                  alt={`${following.nickname}님의 프로필 이미지`}
                  className="w-10 h-10"
                  href={`/profile/${following.memberId}`}
                />
                <div>
                  <Link href={`/profile/${following.memberId}`} className="ml-2 font-bold text-sm">
                    {following.nickname}
                  </Link>
                </div>
                {member && member.memberId !== following.memberId && (
                  <button
                    type="button"
                    className="ml-auto px-3 py-1.5 rounded-md text-xs bg-main-400 text-white transition-colors hover:bg-main-500"
                    onClick={() => onClickFollowOrUnfollow(following.followed, following.memberId, following.nickname)}
                  >
                    {following.followed ? "언팔로우" : "팔로우"}
                  </button>
                )}
              </li>
            )),
          )}
        </InfiniteScrollContainer>
      </ul>
    </section>
  );
};

export default FollowingModal;
