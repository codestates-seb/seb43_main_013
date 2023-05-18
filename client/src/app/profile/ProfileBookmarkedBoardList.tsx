"use client";

import { usePathname, useRouter } from "next/navigation";

// hook
import { useFetchBookmarkedBoardList } from "@/hooks/query";

// component
import ProfileBoard from "./ProfileBoard";
import Pagination from "@/components/Pagination";
import Skeleton from "@/components/Skeleton";
import NotSearch from "@/components/Svg/NotSearch";

// type
interface Props {
  memberId: number;
  page: number;
}

/** 2023/05/17 - 북마크한 게시글들 - by 1-blue */
const ProfileBookmarkedBoardList: React.FC<Props> = ({ memberId, page }) => {
  const router = useRouter();
  const pathname = usePathname();
  const { data } = useFetchBookmarkedBoardList({ memberId, page, size: 10 });

  const fetchNextPage = (selected: number) => {
    if (!pathname) return;

    router.push(pathname + `?type=bookmarked&page=${selected}`);
  };

  // 스켈레톤 UI
  if (!data)
    return (
      <ul className="w-full space-y-4">
        {Array(10)
          .fill(null)
          .map((v, i) => (
            <Skeleton.ProfileBoard key={i} />
          ))}
      </ul>
    );

  return (
    <>
      {data.data.length === 0 ? (
        <NotSearch />
      ) : (
        <ul className="w-full space-y-4">
          {data.data.map((board) => (
            <ProfileBoard key={board.id} board={board} />
          ))}
        </ul>
      )}

      <Pagination page={data.pageInfo.page} totalPages={data.pageInfo.totalPages} onPageChange={fetchNextPage} />
    </>
  );
};

export default ProfileBookmarkedBoardList;
