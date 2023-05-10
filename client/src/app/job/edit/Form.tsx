"use client";

import { useEffect, useState } from "react";
import { useToast } from "@chakra-ui/react";

// api
import { apiUpdateJobBoard } from "@/apis";

// hook
import { useFetchJobBoard } from "@/hooks/query";

// component
import Input from "@/components/BoardForm/Input";
import Editor from "@/components/Editor";
import Category from "@/components/BoardForm/Category";
import FullSpinner from "@/components/Spinner/FullSpinner";

// type
interface Props {
  boardId: number;
}

/** 2023/05/10 - 구인구직 게시글 수정 form 컴포넌트 - by 1-blue */
const Form: React.FC<Props> = ({ boardId }) => {
  const toast = useToast();

  /** 2023/05/10 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/10 - 선택한 category - by 1-blue */
  const [selectedJobCategory, setSelectedJobCategory] = useState("");

  /** 2023/05/10 - 내용 불러오기 - by 1-blue */
  const { data, isLoading } = useFetchJobBoard({ jobBoardId: boardId });

  /** 2023/05/10 - 작성된 내용 채워넣기 - by 1-blue */
  useEffect(() => {
    if (!data) return;

    setContent(data.data.content);
    setSelectedJobCategory(data.data.jobCategoryName);
  }, [data]);

  /** 2023/05/10 - 구인구직 게시글 수정 - by 1-blue */
  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [title] = values;

    // 제목 유효성 검사
    if (title.trim().length <= 1)
      return toast({
        description: "제목을 두 글자 이상 입력해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    if (content.trim().length <= 100)
      return toast({
        description: "내용이 너무 적습니다!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });

    try {
      // TODO: 유저 식별자 넣어서 보내주기 ( memberId )
      const { jobBoardId } = await apiUpdateJobBoard({
        jobBoardId: boardId,
        title,
        content,
        jobCategoryName: selectedJobCategory,
      });

      toast({
        description: "게시글 수정했습니다.\n수정된 게시글 페이지로 이동됩니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });

      // TODO: 화면 이동 + 스피너
      // router.push(`/job/${jobBoardId}`);
    } catch (error) {
      console.error(error);

      return toast({
        description: "에러가 발생했습니다.\n잠시후에 다시 시도해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    }
  };

  if (isLoading) return <FullSpinner />;

  return (
    <form className="flex flex-col space-y-4 mb-4 px-4" onSubmit={onSubmit}>
      {/* title, link, tag, category, thumbnail */}
      <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link, tag, category */}
        <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
          <Input name="제목" type="text" placeholder="제목을 입력해주세요!" defaultValue={data?.data.title} />
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <Category type="job" selectedCategory={selectedJobCategory} setSelectedCategory={setSelectedJobCategory} />
          </div>
        </div>
      </section>

      {/* wysiwyg */}
      <section className="flex flex-col space-y-1">
        <label>
          <span className="text-base font-bold text-gray-800">내용</span>
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
