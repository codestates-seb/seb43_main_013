"use client";

import { useState } from "react";
import { useToast } from "@chakra-ui/react";
import { useRouter } from "next/navigation";

// api
import { apiCreateFreeBoard } from "@/apis";

// store
import { useLoadingStore } from "@/store";

// hook
import useTags from "@/hooks/useTags";

// component
import Input from "@/components/Board/Form/Input";
import Editor from "@/components/Editor";
import Category from "@/components/Board/Form/Category";
import Tag from "@/components/Board/Form/Tag";

/** 2023/05/08 - 자유 게시글 작성 form 컴포넌트 - by 1-blue */
const Form = () => {
  const toast = useToast();
  const router = useRouter();
  const { start, end } = useLoadingStore((state) => state);

  /** 2023/05/09 - 작성한 태그들 - by 1-blue */
  const [selectedTags, onSelectedTag, onDeleteTag] = useTags();

  /** 2023/05/08 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/09 - 선택한 category - by 1-blue */
  const [selectedNormalCategory, setSelectedNormalCategory] = useState("");

  /** 2023/05/08 - 자유 게시글 생성 - by 1-blue */
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
      start();

      // TODO: 유저 식별자 넣어서 보내주기 ( memberId )
      const { freeBoardId } = await apiCreateFreeBoard({
        memberId: 1,
        title,
        tag: selectedTags,
        categoryName: selectedNormalCategory,
        content,
      });

      end();

      toast({
        description: "게시글 생성했습니다.\n생성된 게시글 페이지로 이동됩니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });

      router.push(`/free/${freeBoardId}`);
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

  return (
    <form className="flex flex-col space-y-4 px-4 p-8 bg-white shadow-lg m-4 mt-0 rounded-md" onSubmit={onSubmit}>
      {/* title, link, tag, category, thumbnail */}
      <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link, tag, category */}
        <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
          <Input name="제목" type="text" placeholder="제목을 입력해주세요!" />
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <Input name="태그" type="text" placeholder="태그를 입력해주세요!" noMessage onKeyDown={onSelectedTag} />
            <Category
              type="normal"
              selectedCategory={selectedNormalCategory}
              setSelectedCategory={setSelectedNormalCategory}
            />
          </div>
        </div>
      </section>

      {/* select tags */}
      <ul className="flex space-x-2" onClick={onDeleteTag}>
        {selectedTags.map((selectedTag) => (
          <Tag key={selectedTag} selectedTag={selectedTag} />
        ))}
      </ul>

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
        작성 완료
      </button>
    </form>
  );
};

export default Form;
