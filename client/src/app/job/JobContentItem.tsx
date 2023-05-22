import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import { BookmarkIcon as BookmarkIconChecked } from "@heroicons/react/24/solid";

import ContentFooter from "../../components/BoardMain/ContentFooter";
import TagItem from "../../components/BoardMain/TagItem";
import { JobBoard } from "@/types/api";
import Link from "next/link";
import useCustomToast from "@/hooks/useCustomToast";
import { useMemberStore } from "@/store/useMemberStore";
import { useQueryClient } from "@tanstack/react-query";
import { usePageStore } from "@/store";
import { apiCreateBookmark, apiDeleteBookmark } from "@/apis";
import { isAxiosError } from "axios";

interface JobContentItemProps {
  props: JobBoard;
}

/** 2023/05/18 - 구인구직 게시판 메인 화면 게시글 - by leekoby */
const JobContentItem: React.FC<JobContentItemProps> = ({ props }) => {
  const type = "job";
  const boardId = Number(props[`${type}BoardId`]);
  const toast = useCustomToast();
  const { member } = useMemberStore();
  const queryClient = useQueryClient();
  const currentPage = usePageStore((state) => state.currentPage);

  /** 2023/05/19 - 자유게시판 게시글 북마크 - by leekoby */
  const onClickBookmark = async () => {
    if (!member) {
      return toast({ title: "로그인후에 접근해주세요!", status: "error" });
    }

    try {
      if (props.bookmarked) {
        await apiDeleteBookmark(type, { boardId });
        toast({ title: "북마크를 제거했습니다.", status: "success" });
      } else {
        await apiCreateBookmark(type, { boardId });
        toast({ title: "북마크를 눌렀습니다.", status: "success" });
      }

      // FIXME: 시간 남으면 캐싱 무효화에서 수정하기
      queryClient.invalidateQueries([`${type}BoardList`]);
    } catch (error) {
      console.error(error);

      if (isAxiosError(error)) {
        toast({ title: error.response?.data, status: "error" });
      } else {
        toast({ title: "북마크 처리를 실패했습니다.", status: "error" });
      }
    }
  };

  return (
    <>
      {/*  list container */}
      <div className="flex flex-col items-center justify-center mt-2 bg-white rounded-md shadow-md md:flex-row ">
        {/* List Item * */}
        <div className="items-center p-5 bg-sub-100 rounded-md w-full">
          {/* right content */}
          <div className="flex justify-between w-full h-full">
            {/* content header */}
            <div className="flex flex-col justify-between w-full h-full gap-2 p-2 md:p-3">
              <div className="flex items-center justify-between space-x-2">
                {/* rightside title */}
                <Link href={`/job/${props.jobBoardId}`} className="contents">
                  <h3
                    className={`text-xl font-bold text-left hover:underline hover:underline-offset-4 hover:text-blue-600 truncate-1`}
                  >
                    {props.title}
                  </h3>
                </Link>

                {props.bookmarked ? (
                  <BookmarkIconChecked
                    className="w-5 h-5 flex-shrink-0  text-black cursor-pointer hover:text-main-400 hover:stroke-2 active:text-main-500"
                    onClick={onClickBookmark}
                  />
                ) : (
                  <BookmarkIconUnchecked
                    className="w-5 h-5 flex-shrink-0  text-black cursor-pointer hover:text-main-400 hover:stroke-2 active:text-main-500"
                    onClick={onClickBookmark}
                  />
                )}
              </div>
              {/* content body */}
              <p className="flex-1 w-full text-left truncate-1 text-ellipsis overflow-hidden line-clamp-2">
                {props.content.replace(/<[^>]*>?/g, "")}
              </p>
              {/* rightside tag */}
              <div className="flex w-full gap-x-2">
                {props.tags && props.tags.map((item) => <TagItem tag={item.tagName} />)}
              </div>
            </div>
          </div>
          <ContentFooter position="main" footerData={props} type={type} boardId={props.jobBoardId} />
        </div>
      </div>
    </>
  );
};
export default JobContentItem;
