"use client";

import { usePathname, useRouter } from "next/navigation";

// hook
import { useFetchWrittenBoardList } from "@/hooks/query";

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

/** 2023/05/17 - 내가 쓴 게시글들 - by 1-blue */
const ProfileWrittenBoardList: React.FC<Props> = ({ memberId, page }) => {
  const router = useRouter();
  const pathname = usePathname();
  const { data } = useFetchWrittenBoardList({ memberId, page, size: 10 });

  const fetchNextPage = () => {
    if (!pathname) return;

    router.push(pathname + `?type=written&page=${page + 1}`);
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
        <ul className="w-full space-y-8">
          {data.data.map((board) => (
            <ProfileBoard key={board.id} board={board} />
          ))}
        </ul>
      )}

      <Pagination page={data.pageInfo.page} totalPages={data.pageInfo.totalPages} onPageChange={fetchNextPage} />
    </>
  );
};

export default ProfileWrittenBoardList;
