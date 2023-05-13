import Skeleton from ".";

/** 2023/05/12 - 게시판 수정 스켈레톤 UI 컴포넌트 - by 1-blue */
const BoardEdit = () => (
  <article className="flex flex-col space-y-4 px-4 p-8 bg-white shadow-lg m-4 mt-0 rounded-md">
    {/* title, link, tag, category, thumbnail */}
    <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
      {/* title, link, tag, category */}
      <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
        <div className="space-y-1">
          <Skeleton.Square />
          <Skeleton.Text className="h-8" />
        </div>
        <div className="space-y-1">
          <Skeleton.Square />
          <Skeleton.Text className="h-8" />
        </div>
        <div className="flex flex-col pb-3 md:flex-row space-y-4 md:space-x-4 md:space-y-0">
          <div className="flex-1 space-y-1">
            <Skeleton.Square />
            <Skeleton.Text className="h-8" />
          </div>
          <div className="flex-1 space-y-1">
            <Skeleton.Square />
            <Skeleton.Text className="h-8" />
          </div>
        </div>
        <div className="space-y-1">
          <Skeleton.Square />
          <Skeleton.Text className="h-8" />
        </div>
      </div>
      {/* thumbnail( + preview) */}
      <div className="md:w-[400px] flex flex-col space-y-1">
        <Skeleton.Square />
        <Skeleton.Text className="h-full" />
      </div>
    </section>

    {/* select tags */}
    <ul className="flex space-x-2">
      {Array(6)
        .fill(null)
        .map((v, i) => (
          <Skeleton.Square key={i} className="w-10" />
        ))}
    </ul>

    {/* wysiwyg */}
    <section className="flex flex-col space-y-1">
      <Skeleton.Square />

      <Skeleton.Text className="h-[50vw]" />
    </section>

    {/* submit button */}
    <Skeleton.Square className="self-end px-6 py-3 text-base" />
  </article>
);

export default BoardEdit;
