"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";

// api
import { apiUpdateJobBoard } from "@/apis";

// store
import { useLoadingStore } from "@/store";

// hook
import { useFetchJobBoard } from "@/hooks/query";
import { useMemberStore } from "@/store/useMemberStore";
import useCustomToast from "@/hooks/useCustomToast";

// component
import Input from "@/components/Board/Form/Input";
import Editor from "@/components/Editor";
import JobCategory from "@/components/Board/Form/JobCategory";
import Skeleton from "@/components/Skeleton";

// type
interface Props {
  boardId: number;
}

/** 2023/05/10 - 구인구직 게시글 수정 form 컴포넌트 - by 1-blue */
const Form: React.FC<Props> = ({ boardId }) => {
  const toast = useCustomToast();
  const router = useRouter();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();
  const queryClient = useQueryClient();

  /** 2023/05/10 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/10 - 선택한 category - by 1-blue */
  const [selectedJobCategory, setSelectedJobCategory] = useState("");

  /** 2023/05/10 - 내용 불러오기 - by 1-blue */
  const { data, isLoading } = useFetchJobBoard({ jobBoardId: boardId });

  /** 2023/05/10 - 작성된 내용 채워넣기 - by 1-blue */
  useEffect(() => {
    if (!data) return;

    setContent(data.content);
    setSelectedJobCategory(data.jobCategoryName);
  }, [data]);

  /** 2023/05/10 - 구인구직 게시글 수정 - by 1-blue */
  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [title] = values;

    // 제목 유효성 검사
    if (title.trim().length <= 1) return toast({ title: "제목을 두 글자 이상 입력해주세요!", status: "error" });
    const length = content.replace(/<[^>]*>?/g, "").length;
    if (length <= 20) {
      return toast({ title: `내용을 20자 이상 입력해주세요 ( ${length}/20 )`, status: "error" });
    }
    if (selectedJobCategory === "-- 카테고리 선택 --") {
      return toast({ title: `구인구직 카테고리를 선택해주세요!`, status: "error" });
    }

    try {
      loading.start();

      apiUpdateJobBoard({
        jobBoardId: boardId,
        title,
        content,
        jobCategoryName: selectedJobCategory,
      });

      queryClient.invalidateQueries(["jobBoard", boardId + ""]);

      toast({ title: "게시글 수정했습니다.\n수정된 게시글 페이지로 이동됩니다.", status: "success" });

      router.replace(`/job/${boardId}`);
    } catch (error) {
      console.error(error);

      return toast({ title: "에러가 발생했습니다.\n잠시후에 다시 시도해주세요!", status: "error" });
    } finally {
      loading.end();
    }
  };

  if (isLoading) return <Skeleton.BoardEdit />;

  return (
    <form
      className="flex flex-col space-y-4 px-4 p-8 bg-white shadow-black/40 shadow-sm my-12 mx-4 m-4 mt-0 rounded-md"
      onSubmit={onSubmit}
    >
      {/* title, link, tag, category, thumbnail */}
      <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link, tag, category */}
        <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
          <Input isEssential name="제목" type="text" placeholder="제목을 입력해주세요!" defaultValue={data?.title} />
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <JobCategory selectedCategory={selectedJobCategory} setSelectedCategory={setSelectedJobCategory} />
          </div>
        </div>
      </section>

      {/* wysiwyg */}
      <section className="flex flex-col space-y-1">
        <label>
          <span className="text-base font-bold text-sub-800">내용</span>
        </label>
        <Editor content={content} setContent={setContent} />
      </section>

      {/* submit button */}
      <button
        type="submit"
        className="self-end px-3.5 py-2.5 bg-main-400 text-white text-base font-bold rounded-md transition-colors hover:bg-main-500"
      >
        수정 완료
      </button>
    </form>
  );
};

export default Form;
