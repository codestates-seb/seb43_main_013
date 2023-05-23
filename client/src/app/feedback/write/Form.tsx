"use client";

import { useCallback, useRef, useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { PhotoIcon, ArrowPathIcon, PlusIcon } from "@heroicons/react/24/solid";

// api
import { apiCreateFeedbackBoard } from "@/apis";

// util
import { validateYoutubeURL } from "@/libs";

// hook
import { useLoadingStore } from "@/store";
import useCustomToast from "@/hooks/useCustomToast";

// hook
import useTags from "@/hooks/useTags";
import { useMemberStore } from "@/store/useMemberStore";

// component
import Input from "@/components/Board/Form/Input";
import Editor from "@/components/Editor";
import Tag from "@/components/Board/Form/Tag";
import NormalCategory from "@/components/Board/Form/NormalCategory";
import FeedbackCategory from "@/components/Board/Form/FeedbackCategory";

/** 2023/05/09 - 피드백 게시글 작성 form 컴포넌트 - by 1-blue */
const Form = () => {
  const toast = useCustomToast();
  const router = useRouter();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();

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

    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [title, link] = values;

    // 제목 유효성 검사
    if (title.trim().length <= 1) {
      return toast({ title: "제목을 두 글자 이상 입력해주세요!", status: "error" });
    }
    // 썸네일이나 링크중 하나는 있는지 확인
    if (thumbnail?.length === 0 && link.length === 0) {
      return toast({ title: "썸네일이나 링크중 하나는 입력해주세요!", status: "error" });
    }
    // 유효한 URL인지 확인
    if (!validateYoutubeURL(link)) {
      return toast({ title: "유효한 링크를 입력해주세요!", status: "error" });
    }
    const length = content.replace(/<[^>]*>?/g, "").length;
    if (length <= 20) {
      return toast({ title: `내용을 20자 이상 입력해주세요 ( ${length}/20 )`, status: "error" });
    }
    if (selectedNormalCategory === "-- 카테고리 선택 --") {
      return toast({ title: `일반 카테고리를 선택해주세요!`, status: "error" });
    }
    if (selectedFeedbackCategory === "-- 카테고리 선택 --") {
      return toast({ title: `피드백 카테고리를 선택해주세요!`, status: "error" });
    }
    try {
      loading.start();

      const { feedbackBoardId } = await apiCreateFeedbackBoard({
        memberId: member.memberId,
        title,
        link,
        content,
        tags: selectedTags.map((tag) => ({ tagName: tag })),
        categoryName: selectedNormalCategory,
        feedbackCategoryName: selectedFeedbackCategory,
      });

      toast({ title: "게시글 생성했습니다.\n생성된 게시글 페이지로 이동됩니다.", status: "success" });

      router.push(`/feedback/${feedbackBoardId}`);
    } catch (error) {
      console.error(error);

      return toast({ title: "에러가 발생했습니다.\n잠시후에 다시 시도해주세요!", status: "error" });
    } finally {
      loading.end();
    }
  };

  const [isFocus, setIsFocus] = useState(false);

  return (
    <form
      className="flex flex-col space-y-4 px-4 p-8 bg-white shadow-black/40 shadow-sm my-12 mx-4 m-4 mt-0 rounded-md"
      onSubmit={onSubmit}
    >
      {/* title, link, tag, category, thumbnail */}
      <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link, tag, category */}
        <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
          <Input name="제목" type="text" placeholder="제목을 입력해주세요!" />
          <Input name="유튜브 링크" type="text" placeholder="유튜브 링크을 입력해주세요!" />
          <div className="flex flex-col pb-3 md:flex-row space-y-4 md:space-x-4 md:space-y-0">
            <NormalCategory selectedCategory={selectedNormalCategory} setSelectedCategory={setSelectedNormalCategory} />
            <FeedbackCategory
              selectedCategory={selectedFeedbackCategory}
              setSelectedCategory={setSelectedFeedbackCategory}
            />
          </div>
          <div className="relative z-10 flex-1">
            <Input
              name="태그"
              type="text"
              placeholder="태그를 입력해주세요!"
              noMessage
              onKeyDown={onSelectedTag}
              onFocus={() => setIsFocus(true)}
              onBlur={() => setIsFocus(false)}
            />
            {isFocus && selectedTags.length === 0 && (
              <p className="whitespace-pre text-sm bg-main-500 shadow-md text-white px-3 py-2 absolute -bottom-16 left-0 rounded-md animate-fade-in">
                {"엔터키를 이용하면 태그를 등록할 수 있습니다.\n그리고 태그를 클릭하면 태그가 제거됩니다."}
              </p>
            )}
          </div>
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
