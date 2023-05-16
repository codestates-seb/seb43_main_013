import type { FreeBoard } from "@/types/api";

interface Props extends Pick<FreeBoard, "content"> {}

/** 2023/05/11 - 게시판 내용 컴포넌트 - by 1-blue */
const BoardContent: React.FC<Props> = ({ content }) => {
  return <section dangerouslySetInnerHTML={{ __html: content }} className="viewer py-4" />;
};

export default BoardContent;
