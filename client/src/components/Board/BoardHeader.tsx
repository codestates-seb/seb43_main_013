import { useCallback } from "react";
import { usePathname } from "next/navigation";
import { useToast } from "@chakra-ui/react";
import moment from "moment";
import { BookmarkIcon as OBookmarkIcon, LinkIcon as OLinkIcon, EyeIcon as OEyeIcon } from "@heroicons/react/24/outline";
// import { LinkIcon as SLinkIcon } from "@heroicons/react/24/solid";

// component
import Avatar from "@/components/Avatar";

// type
import type { Board } from "@/types/api";
interface Props extends Board {
  tag?: string[];
  categoryName?: string;
  feedbackCateogoryName?: string;
  jobCategoryName?: string;
  channelName?: string;
  subscriberCount?: number;
}

/** 2023/05/11 - 보드 상단 컴포넌트 - by 1-blue */
const BoardHeader: React.FC<Props> = ({
  title,
  tag,
  memberId,
  nickname,
  createdAt,
  profileImageUrl,
  viewCount,
  categoryName,
  feedbackCateogoryName,
  jobCategoryName,
  channelName,
  subscriberCount,
}) => {
  const toast = useToast();
  const pathname = usePathname();

  /** 2023/04/11 - copy clipboard - by 1-blue */
  const copyLink = useCallback(() => {
    navigator.clipboard.writeText(window.location.origin + pathname).then(() =>
      toast({
        description: "링크를 복사했습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      }),
    );
  }, []);

  return (
    <>
      {/* 아바타, 북마크, 링크 */}
      <section className="flex">
        <Avatar src={profileImageUrl} alt={`${nickname}님의 프로필 이미지`} className="w-16 h-16 object-fill" />

        <button type="button" className="ml-auto" onClick={copyLink}>
          <OLinkIcon className="w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500" />
        </button>
        <button type="button" className="ml-2">
          {/* TODO: 조건부 */}
          <OBookmarkIcon className="w-6 h-6 hover:text-main-400 hover:stroke-2 active:text-main-500" />
          {/* <SBookmarkIcon className="w-6 h-6" /> */}
        </button>
      </section>

      {/* 제목 */}
      <section>
        <h1 className="text-2xl font-bold truncate">{title}</h1>
      </section>

      {/* 작성자 이름 / 작성일 */}
      <section className="flex items-center">
        <span className="text-gray-400 after:content-['|'] after:mx-2 after:text-gray-400">{nickname}</span>
        <span className="text-gray-400 text-sm">{moment(createdAt).endOf("day").fromNow()}</span>

        <OEyeIcon className="ml-auto w-6 h-6 text-gray-500" />
        <span className="ml-1 text-sm text-gray-500">{viewCount.toLocaleString()}</span>
      </section>

      {/* 카테고리 || 채널명/구독자수 */}
      <section className="flex">
        {channelName && subscriberCount && (
          <div className="flex flex-col mr-auto">
            {<span className="font-semibold text-xs text-gray-500">채널명: {channelName}</span>}
            {<span className="font-semibold text-xs text-gray-500">구독자: {subscriberCount.toLocaleString()}</span>}
          </div>
        )}

        <div>
          {categoryName && <span className="text-base font-semibold text-main-500">{categoryName}</span>}
          {feedbackCateogoryName && (
            <span className="text-base font-semibold text-main-500 before:content-['|'] before:mx-2 before:text-main-400">
              {feedbackCateogoryName}
            </span>
          )}
          {jobCategoryName && <span className="text-base font-semibold text-main-500">{jobCategoryName}</span>}
        </div>
      </section>

      {/* 태그 */}
      <ul className="flex space-x-2 flex-wrap">
        {tag?.map((tag) => (
          <li key={tag} className="px-2 py-1 mt-1 text-xs font-bold text-main-400 border-2 border-main-400 rounded-lg">
            {tag}
          </li>
        ))}
      </ul>
    </>
  );
};

export default BoardHeader;
