"use client";

import { useRef, useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { useToast } from "@chakra-ui/react";
import { PhotoIcon, ArrowPathIcon } from "@heroicons/react/24/solid";

// api
import { apiCreateFeedbackBoard } from "@/apis";

// util
import { validateYoutubeURL } from "@/libs";

// hook
import { useLoadingStore } from "@/store";

// hook
import useTags from "@/hooks/useTags";

// component
import Input from "@/components/Board/Form/Input";
import Editor from "@/components/Editor";
import Category from "@/components/Board/Form/Category";
import Tag from "@/components/Board/Form/Tag";

/** 2023/05/09 - 피드백 게시글 작성 form 컴포넌트 - by 1-blue */
const Form = () => {
  const toast = useToast();
  const router = useRouter();
  const { start, end } = useLoadingStore((state) => state);

  /** 2023/05/09 - 작성한 태그들 - by 1-blue */
  const [selectedTags, onSelectedTag, onDeleteTag] = useTags();

  /** 2023/05/09 - 썸네일 ref - by 1-blue */
  const ThumbnailRef = useRef<HTMLInputElement>(null);

  /** 2023/05/09 - thumbnail - by 1-blue */
  const [thumbnail, setThumbnail] = useState<FileList | null>(null);
  /** 2023/05/09 - preview - by 1-blue */
  const [preview, setPreview] = useState("");

  /** 2023/05/09 - preview 등록 - by 1-blue */
  const onUploadPreview: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setThumbnail(e.target.files);

    // 이미 프리뷰가 있다면 제거 ( GC에게 명령 )
    if (preview) URL.revokeObjectURL(preview);

    // 썸네일이 입력되면 브라우저에서만 보여줄 수 있도록 blob url 얻기
    if (e.target.files && e.target.files.length > 0) {
      setPreview(URL.createObjectURL(e.target.files[0]));
    }
  };

  /** 2023/05/09 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/09 - 선택한 category - by 1-blue */
  const [selectedNormalCategory, setSelectedNormalCategory] = useState("");
  const [selectedFeedbackCategory, setSelectedFeedbackCategory] = useState("");

  /** 2023/05/09 - 피드백 게시글 생성 - by 1-blue */
  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
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
    if (!validateYoutubeURL(link))
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

    try {
      start();

      // TODO: memberId && thumbnail url 넣어서 보내주기 ( memberId )
      const { feedbackBoardId } = await apiCreateFeedbackBoard({
        memberId: 1,
        title,
        link,
        content,
        tag: selectedTags,
        categoryName: selectedNormalCategory,
        feedbackCateogoryName: selectedFeedbackCategory,
      });

      end();

      toast({
        description: "게시글 생성했습니다.\n생성된 게시글 페이지로 이동됩니다.",
        status: "success",
        duration: 2500,
        isClosable: true,
      });

      router.push(`/feedback/${feedbackBoardId}`);
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
          <Input name="유튜브 링크" type="text" placeholder="유튜브 링크을 입력해주세요!" />
          <div className="flex flex-col pb-3 md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <Category
              type="normal"
              selectedCategory={selectedNormalCategory}
              setSelectedCategory={setSelectedNormalCategory}
            />
            <Category
              type="feedback"
              selectedCategory={selectedFeedbackCategory}
              setSelectedCategory={setSelectedFeedbackCategory}
            />
          </div>
          <Input name="태그" type="text" placeholder="태그를 입력해주세요!" noMessage onKeyDown={onSelectedTag} />
        </div>
        {/* thumbnail( + preview) */}
        <div className="md:w-[400px] flex flex-col">
          <label>
            <span className="text-base font-bold text-sub-800 mb-1">썸네일</span>
          </label>
          <figure className="group pt-[60%] md:pt-0 flex-1 relative border-2 border-dotted border-black rounded-md p-2">
            <input type="file" hidden ref={ThumbnailRef} onChange={onUploadPreview} />

            <button
              type="button"
              className="absolute inset-1/2 w-[calc(100%-0.5rem)] h-[calc(100%-0.5rem)] -translate-x-1/2 -translate-y-1/2 flex justify-center items-center bg-transparent rounded-md transition-colors group-hover:z-[1] group-hover:bg-black/70"
              onClick={() => ThumbnailRef.current?.click()}
            >
              {preview ? (
                <ArrowPathIcon className="w-12 h-12 text-sub-300 z-[1] transition-colors group-hover:text-sub-200" />
              ) : (
                <PhotoIcon className="w-12 h-12 text-sub-400 z-[1] transition-colors group-hover:text-sub-200" />
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
          <span className="text-base font-bold text-sub-800">내용</span>
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
