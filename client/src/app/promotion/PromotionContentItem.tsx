import Link from "next/link";
import Image from "next/image";
import { forwardRef } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { isAxiosError } from "axios";

import { PromotionBoard } from "@/types/api";
import { apiCreateBookmark, apiDeleteBookmark } from "@/apis";

import TagItem from "../../components/BoardMain/TagItem";
import ContentFooter from "../../components/BoardMain/ContentFooter";
import BoardThumbnail from "@/components/Board/BoardThumbnail";
import useCustomToast from "@/hooks/useCustomToast";

import { useMemberStore } from "@/store/useMemberStore";

import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import { BookmarkIcon as BookmarkIconChecked } from "@heroicons/react/24/solid";

interface ContentItemProps {
  props: PromotionBoard;
  position: "main" | "board";
}

/** 2023/05/17 - 홍보 게시판 게시글 - by leekoby */

const PromotionContentItem = forwardRef<HTMLDivElement, ContentItemProps>(({ props, position }, ref) => {
  const type = "promotion";
  const toast = useCustomToast();
  const { member } = useMemberStore();
  const queryClient = useQueryClient();
  const boardId = Number(props[`${type}BoardId`]);

  /** 2023/05/24 - 홍보 게시판 게시글 북마크 - by leekoby */
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
        className=" flex flex-col items-center p-3 bg-white rounded-md shadow-md md:flex-row md:w-full shadow-black/20 hover:shadow-black/30 hover:shadow-lg transition-all h-full"
      >
        {/* 게시글 영역 */}
        <div className="flex flex-col items-center w-full h-full p-5 bg-sub-100 rounded-md ">
          {/* Thumbnail */}
          {props.link && (
            <Link href={`/promotion/${props.promotionBoardId}`} className=" w-full flex items-center justify-center">
              <BoardThumbnail
                url={props.link}
                alt={props.title}
                className="max-w-[640px] max-h-[360px] min-h[240px] flex-shrink-0"
                position={position}
              />
            </Link>
          )}
          {/* right content */}
          {/* 채널정보 영역 */}
          {/* <div className="flex">
            <h3 className="text-xl font-bold text-left hover:underline hover:underline-offset-4 hover:text-blue-600 truncate-1">
              {props.channelName}
            </h3>
            <div className="text-xs text-sub-500">구독자 수 {props.subscriberCount}</div>
          </div> */}

          <div className="flex flex-col w-full h-full gap-2 p-2">
            {/* content header */}
            <div className="flex items-center justify-between space-x-2 mt-2">
              {/* 게시글 제목 */}
              <Link href={`/promotion/${props.promotionBoardId} `} className="contents">
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
            <p className="text-md flex-1 w-full text-left truncate-1 text-ellipsis overflow-hidden line-clamp-1">
              {props.content.replace(/<[^>]*>?/g, "")}
            </p>
            {/* rightside tag */}
            {props.tags && <TagItem tags={props.tags} />}
          </div>
          <ContentFooter position="main" footerData={props} type={type} boardId={props.promotionBoardId} />
        </div>
      </div>
    </>
  );
});

export default PromotionContentItem;
