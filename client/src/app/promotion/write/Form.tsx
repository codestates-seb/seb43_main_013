"use client";

import { useState } from "react";
import { useToast } from "@chakra-ui/react";

// util
import { validateYoutubeURL } from "@/libs";

// hook
import useTags from "@/hooks/useTags";

// component
import Input from "@/components/BoardForm/Input";
import Editor from "@/components/Editor";
import Category from "@/components/BoardForm/Category";
import Tag from "@/components/BoardForm/Tag";

/** 2023/05/09 - 홍보 게시글 작성 form 컴포넌트 - by 1-blue */
const Form = () => {
  const toast = useToast();

  /** 2023/05/09 - 작성한 태그들 - by 1-blue */
  const [selectedTags, onSelectedTag, onDeleteTag] = useTags();

  /** 2023/05/09 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/09 - 선택한 category - by 1-blue */
  const [selectedJobCategory, setSelectedJobCategory] = useState("");

  /** 2023/05/09 - 홍보 게시글 생성 - by 1-blue */
  const onSubmit: React.FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [title, link, channelName, subscriberCount] = values;

    // 제목 유효성 검사
    if (title.trim().length <= 1)
      return toast({
        description: "제목을 두 글자 이상 입력해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    // 유효한 URL인지 확인
    if (!validateYoutubeURL(link))
      return toast({
        description: "유효한 링크를 입력해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    // 채널명 유효성 검사
    if (channelName.trim().length <= 0)
      return toast({
        description: "채널명을 한 글자 이상 입력해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    if (+subscriberCount <= 0)
      return toast({
        description: "구독자 수를 0명 이상으로 입력해주세요!",
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

    // TODO: 유저 식별자 넣어서 보내주기 ( memberId )
    const body = {
      title,
      link,
      channelName,
      subscriberCount,
      selectedTags,
      selectedJobCategory,
      content,
    };

    console.log("body >> ", body);
  };

  return (
    <form className="flex flex-col space-y-4 mb-4 px-4" onSubmit={onSubmit}>
      {/* title, link, tag, category, thumbnail */}
      <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link, tag, category */}
        <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
          <Input name="제목" type="text" placeholder="제목을 입력해주세요!" />
          <Input name="유튜브 링크" type="text" placeholder="유튜브 링크을 입력해주세요!" />
          <div className="flex flex-col md:flex-row md:space-x-4">
            <Input name="채널명" type="text" placeholder="채널명을 입력해주세요!" />
            <Input name="구독자 수" type="number" placeholder="구독자 수를 입력해주세요!" />
          </div>
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <Input name="태그" type="text" placeholder="태그를 입력해주세요!" noMessage onKeyDown={onSelectedTag} />
            <Category type="job" selectedCategory={selectedJobCategory} setSelectedCategory={setSelectedJobCategory} />
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
