"use client";

import { useCallback, useRef, useState } from "react";
import Image from "next/image";
import { useToast } from "@chakra-ui/react";
import { PhotoIcon, ArrowPathIcon } from "@heroicons/react/24/solid";

// util
import { validateURL } from "@/libs";

// component
import Input from "@/components/Form/Input";
import Editor from "@/components/Editor";
import Category from "./Category";

// 임의로 정의한 태그들
const dummyTags = [
  "일단",
  "모르겠다",
  "아무거나",
  "넣어보자",
  "나는",
  "내일",
  "어제의",
  "너와",
  "만난다",
  "오늘",
  "점심은",
  "라면",
  "그리고",
  "하지만",
  "사건의지평선",
  "블랙홀",
];

// 임의로 정의한 카테고리
const dummuCategories = [
  "먹방",
  "게임",
  "스포츠",
  "뷰티",
  "이슈",
  "음악",
  "쿠킹",
  "동물",
  "영화",
  "드라마",
  "디자인",
  "코딩",
];

/** 2023/05/08 - 자유 게시글 작성 form 컴포넌트 - by 1-blue */
const Form = () => {
  const toast = useToast();

  /** 2023/05/08 - 선택한 태그들 - by 1-blue */
  const [selectedTags, setSelectedTags] = useState<string[]>(["태그", "게임", "채팅"]);

  /** 2023/05/08 - 태그 추가 - by 1-blue */
  const onSelectedTag: React.KeyboardEventHandler<HTMLInputElement> = useCallback(
    (e) => {
      // 엔터키 확인
      if (e.key !== "Enter") return;

      const { target } = e;

      // submit 방지
      e.preventDefault();

      // input 확인
      if (!(target instanceof HTMLInputElement)) return;

      // 정의된 태그가 아닌 경우
      if (!dummyTags.some((tag) => target.value === tag)) {
        return toast({
          description: "사용할 수 없는 태그입니다.",
          status: "warning",
          duration: 2500,
          isClosable: true,
        });
      }

      // 태그가 이미 존재하는 경우
      if (selectedTags.some((selectedTag) => target.value === selectedTag)) {
        return toast({
          description: "이미 존재하는 태그입니다.",
          status: "warning",
          duration: 2500,
          isClosable: true,
        });
      }

      const tag = target.value;

      // 태그 추가
      setSelectedTags((tags) => [...tags, tag]);

      // 리셋
      target.value = "";

      toast({
        description: "태그를 추가했습니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });
    },
    [selectedTags],
  );

  /** 2023/05/08 - 태그 제거 ( 버블링 ) - by 1-blue */
  const onDeleteTag: React.MouseEventHandler<HTMLUListElement> = ({ target }) => {
    if (!(target instanceof HTMLButtonElement)) return;
    if (!target.dataset.tag) return;

    setSelectedTags((selectedTags) => selectedTags.filter((selectedTag) => selectedTag !== target.dataset.tag));

    toast({
      description: "태그를 제거했습니다.",
      status: "success",
      duration: 2500,
      isClosable: true,
    });
  };

  /** 2023/05/08 - 썸네일 ref - by 1-blue */
  const ThumbnailRef = useRef<HTMLInputElement>(null);

  /** 2023/05/08 - thumbnail - by 1-blue */
  const [thumbnail, setThumbnail] = useState<FileList | null>(null);
  /** 2023/05/08 - preview - by 1-blue */
  const [preview, setPreview] = useState("");

  /** 2023/05/08 - preview 등록 - by 1-blue */
  const onUploadPreview: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setThumbnail(e.target.files);

    // 이미 프리뷰가 있다면 제거 ( GC에게 명령 )
    if (preview) URL.revokeObjectURL(preview);

    // 썸네일이 입력되면 브라우저에서만 보여줄 수 있도록 blob url 얻기
    if (e.target.files && e.target.files.length > 0) {
      setPreview(URL.createObjectURL(e.target.files[0]));
    }
  };

  /** 2023/05/08 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/08 - 자유 게시글 생성 - by 1-blue */
  const onSubmit: React.FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [title, link] = values;

    // 제목 유효성 검사
    if (title.trim().length <= 1)
      return toast({
        description: "제목을 두 글자 이상 입력해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    // 썸네일이나 링크중 하나는 있는지 확인
    if (thumbnail?.length === 0 && link.length === 0)
      return toast({
        description: "썸네일이나 링크중 하나는 입력해주세요!",
        status: "error",
        duration: 2500,
        isClosable: true,
      });
    // 유효한 URL인지 확인
    if (!validateURL(link))
      return toast({
        description: "유효한 링크를 입력해주세요!",
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

    const body = {
      title,
      link,
      selectedTags,
      content,
      thumbnail,
    };

    console.log("body >> ", body);
  };

  return (
    <form className="flex flex-col space-y-4 mb-4 px-4" onSubmit={onSubmit}>
      {/* title, link, tag, category, thumbnail */}
      <div className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link, tag, category */}
        <div className="w-full md:w-0 md:flex-grow-[6.5] space-y-2">
          <Input name="제목" type="text" placeholder="제목을 입력해주세요!" />
          <Input name="유튜브 링크" type="text" placeholder="유튜브 링크을 입력해주세요!" />
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <Input name="태그" type="text" placeholder="태그를 입력해주세요!" noMessage onKeyDown={onSelectedTag} />
            <Category categories={dummuCategories} />
          </div>
        </div>
        {/* thumbnail( + preview) */}
        <div className="w-full md:w-0 md:flex-grow-[3.5] flex flex-col">
          <Input name="썸네일" noMessage hidden />
          <figure className="group pt-[60%] md:pt-0 flex-1 relative border-2 border-dotted border-black rounded-md p-2">
            <input type="file" hidden ref={ThumbnailRef} onChange={onUploadPreview} />

            <button
              type="button"
              className="absolute inset-1/2 w-[calc(100%-0.5rem)] h-[calc(100%-0.5rem)] -translate-x-1/2 -translate-y-1/2 flex justify-center items-center bg-transparent rounded-md transition-colors group-hover:z-[1] group-hover:bg-black/70"
              onClick={() => ThumbnailRef.current?.click()}
            >
              {preview ? (
                <ArrowPathIcon className="w-12 h-12 text-gray-300 z-[1] transition-colors group-hover:text-gray-200" />
              ) : (
                <PhotoIcon className="w-12 h-12 text-gray-400 z-[1] transition-colors group-hover:text-gray-200" />
              )}
            </button>

            {preview && (
              <Image
                src={preview}
                alt="업로드한 썸네일"
                fill
                quality={75}
                placeholder="blur"
                blurDataURL={"blurDataURL"}
                className="p-1"
              />
            )}
          </figure>
        </div>
      </div>

      {/* select tags */}
      <ul className="flex space-x-2" onClick={onDeleteTag}>
        {selectedTags.map((selectedTag) => (
          <li key={selectedTag}>
            <button
              type="button"
              className="px-3 py-1 bg-main-400 text-white rounded-sm transition-colors hover:font-bold hover:bg-main-500"
              data-tag={selectedTag}
            >
              {selectedTag}
            </button>
          </li>
        ))}
      </ul>

      {/* wysiwyg */}
      <Editor content={content} setContent={setContent} />

      {/* submit button */}
      <button
        type="submit"
        className="self-end px-4 py-3 bg-main-400 text-white font-bold rounded-md transition-colors hover:bg-main-500"
      >
        작성 완료
      </button>
    </form>
  );
};

export default Form;
