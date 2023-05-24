import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import { BookmarkIcon as BookmarkIconChecked } from "@heroicons/react/24/solid";
import ContentFooter from "../../components/BoardMain/ContentFooter";
import TagItem from "../../components/BoardMain/TagItem";
import { FeedbackBoard } from "@/types/api";
import { forwardRef } from "react";
import Link from "next/link";
import BoardThumbnail from "@/components/Board/BoardThumbnail";
import useCustomToast from "@/hooks/useCustomToast";
import { useMemberStore } from "@/store/useMemberStore";
import { useQueryClient } from "@tanstack/react-query";
import { apiCreateBookmark, apiDeleteBookmark } from "@/apis";
import { isAxiosError } from "axios";

interface ContentItemProps {
  props: FeedbackBoard;
  position: "main" | "board";
}
/** 2023/05/09 - 피드백 게시판 게시글 - by leekoby */
const FeedbackContentItem = forwardRef<HTMLDivElement, ContentItemProps>(({ props, position }, ref) => {
  const type = "feedback";
  const toast = useCustomToast();
  const { member } = useMemberStore();
  const queryClient = useQueryClient();

  const boardId = Number(props[`${type}BoardId`]);
  /** 2023/05/20 - 피드백 게시판 게시글 북마크 - by leekoby */
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

      // TODO: 캐싱 무효화 어떻게 사용하는건지 알아보기
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
      <div
        ref={ref}
        className=" flex flex-col items-center p-3 bg-white rounded-md shadow-md md:flex-row md:w-full shadow-black/20 hover:shadow-black/30 hover:shadow-lg"
      >
        {/* 게시글 영역* */}
        <div className="flex flex-col items-center w-full  h-full p-5  bg-sub-100 rounded-md ">
          {/* Thumnail */}
          <Link href={`/feedback/${props.feedbackBoardId}  `} className=" w-full flex items-center justify-center">
            <BoardThumbnail
              url={props.link}
              alt={props.title}
              className="max-w-[640px] max-h-[360px] min-h[240px] flex-shrink-0"
              position={position}
            />
          </Link>
          {/* right content */}
          <div className="flex flex-col w-full flex-1 gap-2 p-2">
            {/* content header */}
            <div className="flex items-center justify-between space-x-2 mt-2">
              {/* 게시글 제목 */}
              <Link href={`/feedback/${props.feedbackBoardId} `} className="contents">
                <h3 className="text-xl font-bold text-left hover:underline hover:underline-offset-4 hover:text-blue-600 truncate-1">
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
            <p className="truncate-2" dangerouslySetInnerHTML={{ __html: props.content.replace(/<[^>]*>?/g, "") }} />
            {/* 오른쪽 사이드 영역 */}
            <div className="flex gap-x-2">{props.tags && props.tags.map((item) => <TagItem tag={item.tagName} />)}</div>
          </div>
          <ContentFooter position="main" footerData={props} type={type} boardId={props.feedbackBoardId} />
        </div>
      </div>
    </>
  );
});

export default FeedbackContentItem;
