import ContentFooter from "@/components/BoardMain/ContentFooter";
import { NoticeBoard } from "@/types/api";
import Link from "next/link";

// /** 2023/05/20 - 공지사항 메인 화면 게시글 - by leekoby */
interface ContentItemProps {
  props: NoticeBoard;
}
const NoticeContentItem: React.FC<ContentItemProps> = ({ props }) => {
  const type = "notice";
  return (
    <>
      {/*  list container */}
      <div className="flex flex-col items-center justify-center p-2 bg-white rounded-md shadow-md md:flex-row ">
        {/* List Item * */}
        <div className="items-center p-5 bg-sub-100 rounded-md w-full">
          {/* right content */}
          <div className="flex justify-between w-full h-full">
            {/* content header */}
            <div className="flex flex-col justify-between w-full h-full gap-2 p-2 md:p-3">
              <div className="flex items-center justify-between w-full space-x-2">
                {/* 게시글 제목 */}
                <Link href={`/notice/${props.noticeId}`} className="contents">
                  <h3
                    className={`text-xl font-bold text-left hover:underline hover:underline-offset-4 hover:text-blue-600 truncate-1 `}
                  >
                    {props.title}
                  </h3>
                </Link>
              </div>
              {/* content body */}
              <p className="flex-1 w-full text-left truncate-1 text-ellipsis overflow-hidden line-clamp-2 ">
                {props.content.replace(/<[^>]*>?/g, "")}
              </p>
            </div>
          </div>
          <ContentFooter position="main" footerData={props} type={type} boardId={props.noticeId} />
        </div>
      </div>
    </>
  );
};
export default NoticeContentItem;
