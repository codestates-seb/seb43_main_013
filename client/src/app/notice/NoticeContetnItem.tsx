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
        <div className="flex flex-col items-center w-full h-full p-5 bg-sub-100 rounded-md ">
          {/* Thumnail */}
          {/* <Image src={defaultThumnail} className="h-auto mx-1 my-1 border rounded-lg w-80 md:w-40" alt="" /> */}
          {/* right content */}
          <div className="flex justify-between w-full h-full">
            {/* content header */}
            <div className="flex flex-col justify-between w-full h-full gap-2 p-2 md:p-3">
              <div className="flex items-center justify-between ">
                {/* 게시글 제목 */}
                <Link href={`/notice/${props.noticeId}`}>
                  <h1
                    className={`text-xl font-bold text-left hover:underline hover:underline-offset-4 hover:text-blue-600 truncate `}
                  >
                    {props.title}
                  </h1>
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
