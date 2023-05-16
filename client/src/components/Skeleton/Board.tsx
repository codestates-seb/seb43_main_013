import { twMerge } from "tailwind-merge";

// component
import Skeleton from ".";

/** 2023/05/12 - 게시판 스켈레톤 UI 컴포넌트 - by 1-blue */
const Board = () => (
  <article className="p-8 space-y-2 bg-white shadow-lg m-4 rounded-md">
    {/* 아바타, 북마크, 링크 */}
    <section className="flex">
      <Skeleton.Circle className="w-16 h-16" />
      <Skeleton.Square className="ml-auto w-7 h-7" />
      <Skeleton.Square className="ml-2 w-7 h-7" />
    </section>

    {/* 제목 */}
    <section>
      <Skeleton.Text className="h-8" />
    </section>

    {/* 작성자 이름 / 작성일 */}
    <section className="flex items-center">
      <Skeleton.Square className="h-5" />
      <span className="mx-2 ">|</span>
      <Skeleton.Square className="h-5" />

      <Skeleton.Square className="ml-auto w-5 h-5" />
      <Skeleton.Square className="ml-1 h-5" />
    </section>

    {/* 태그 */}
    <ul className="flex space-x-2 flex-wrap">
      {Array(8)
        .fill(null)
        .map((v, i) => (
          <Skeleton.Square key={i} className="px-2 py-1 mt-1" />
        ))}
    </ul>

    {/* 라인 */}
    <div>
      <hr className="h-0.5 bg-sub-200 my-6" />
    </div>

    {/* 내용 */}
    <ul className="space-y-2 py-4">
      {Array(36)
        .fill(null)
        .map((v, i) => (
          <li key={i}>
            <Skeleton.Text
              className={twMerge(
                Math.random() > 0.8
                  ? "w-full"
                  : Math.random() > 0.6
                  ? "w-4/5"
                  : Math.random() > 0.4
                  ? "w-3/5"
                  : Math.random() > 0.4
                  ? "w-2/5"
                  : "w-1/5",
              )}
            />
          </li>
        ))}
    </ul>

    <section className="flex justify-between items-center">
      <div className="flex space-x-1">
        <Skeleton.Square className="w-5 h-5" />

        <Skeleton.Square className="" />
      </div>

      <div className="flex space-x-1">
        <Skeleton.Square className="w-5 h-5" />
        <Skeleton.Square className="" />
      </div>
    </section>
  </article>
);

export default Board;
