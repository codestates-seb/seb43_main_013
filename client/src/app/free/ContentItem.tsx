import Image from "next/image";
import { BookmarkIcon as BookmarkIconUnchecked } from "@heroicons/react/24/outline";
import { BookmarkIcon as BookmarkIconChecked } from "@heroicons/react/24/solid";

import ContentFooter from "../../components/BoardMain/ContentFooter";
import TagItem from "../../components/BoardMain/TagItem";
import { FreeBoard } from "@/types/api";
import Link from "next/link";
import { useMemberStore } from "@/store/useMemberStore";
import { Toast } from "@chakra-ui/react";
import { apiCreateBookmark, apiDeleteBookmark } from "@/apis";
import useCustomToast from "@/hooks/useCustomToast";
import { useQueryClient } from "@tanstack/react-query";
import { isAxiosError } from "axios";
import { usePageStore } from "@/store";

// /** 2023/05/08 - 자유게시판 메인 화면 게시글 - by leekoby */
interface ContentItemProps {
  props: FreeBoard;
}

const ContentItem: React.FC<ContentItemProps> = ({ props }) => {
  const type = "free";
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
      queryClient.invalidateQueries([`${type}BoardList`, currentPage]);
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
      <div className="flex flex-col items-center justify-center p-2 bg-white rounded-md shadow-main md:flex-row ">
        {/* List Item * */}
        <div className="items-center p-5 bg-sub-100 rounded-md w-full">
          {/* Thumnail */}
          {/* <Image src={defaultThumnail} className="h-auto mx-1 my-1 border rounded-lg w-80 md:w-40" alt="" /> */}
          {/* right content */}
          <div className="flex justify-between w-full h-full">
            {/* content header */}
            <div className="flex flex-col justify-between w-full h-full gap-2 p-2 md:p-3">
              <div className="flex items-center justify-between w-full space-x-2">
                {/* 게시글 제목 */}
                <Link href={`/free/${props.freeBoardId}`} className="contents">
                  <h3
                    className={`text-xl font-bold text-left hover:underline hover:underline-offset-4 hover:text-blue-600 truncate `}
                  >
                    {/* {props.title} */}
                    aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
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
              <p className="flex-1 w-full text-left truncate-1 text-ellipsis overflow-hidden line-clamp-2 ">
                {props.content.replace(/<[^>]*>?/g, "")}
              </p>
              {/* rightside tag */}
              <div className="flex w-full gap-x-2">
                {props.tags && props.tags.map((item) => <TagItem tag={item.tagName} />)}
              </div>
            </div>
          </div>
          <ContentFooter position="main" footerData={props} type={type} boardId={props.freeBoardId} />
        </div>
      </div>
    </>
  );
};

export default ContentItem;
