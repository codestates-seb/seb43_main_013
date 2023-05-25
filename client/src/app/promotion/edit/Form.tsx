"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";

// api
import { apiUpdatePromotionBoard } from "@/apis";

// util
import { validateYoutubeURL } from "@/libs";

// store
import { useLoadingStore } from "@/store";

// hook
import useTags from "@/hooks/useTags";
import { useFetchPromotionBoard } from "@/hooks/query";
import { useMemberStore } from "@/store/useMemberStore";
import useCustomToast from "@/hooks/useCustomToast";

// component
import Input from "@/components/Board/Form/Input";
import Editor from "@/components/Editor";
import NormalCategory from "@/components/Board/Form/NormalCategory";
import Tag from "@/components/Board/Form/Tag";
import Skeleton from "@/components/Skeleton";

// type
interface Props {
  boardId: number;
}

/** 2023/05/09 - 홍보 게시글 작성 form 컴포넌트 - by 1-blue */
const Form: React.FC<Props> = ({ boardId }) => {
  const toast = useCustomToast();
  const router = useRouter();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();
  const queryClient = useQueryClient();

  /** 2023/05/09 - 작성한 태그들 - by 1-blue */
  const [selectedTags, onSelectedTag, onDeleteTag, setSelectedTags] = useTags();

  /** 2023/05/09 - wysiwyg 으로 받는 content - by 1-blue */
  const [content, setContent] = useState("");

  /** 2023/05/09 - 선택한 category - by 1-blue */
  const [selectedNormalCategory, setSelectedNormalCategory] = useState("");

  /** 2023/05/10 - 내용 불러오기 - by 1-blue */
  const { data, isLoading } = useFetchPromotionBoard({ promotionBoardId: boardId });

  /** 2023/05/10 - 작성된 내용 채워넣기 - by 1-blue */
  useEffect(() => {
    if (!data) return;

    setSelectedTags(data.tags.map((tag) => tag.tagName));
    setContent(data.content);
    setSelectedNormalCategory(data.categoryName);
  }, [data]);

  /** 2023/05/09 - 홍보 게시글 수정 - by 1-blue */
  const onSubmit: React.FormEventHandler<HTMLFormElement> = async (e) => {
    e.preventDefault();

    if (!member) return toast({ title: "로그인후에 접근해주세요!", status: "error" });

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [title, link, channelName, subscriberCount] = values;

    // 제목 유효성 검사
    if (title.trim().length <= 1) return toast({ title: "제목을 두 글자 이상 입력해주세요!", status: "error" });
    // 유효한 URL인지 확인
    if (!validateYoutubeURL(link)) return toast({ title: "유튜브 영상 링크를 입력해주세요!", status: "error" });
    // 채널명 유효성 검사
    if (channelName.trim().length <= 0) return toast({ title: "채널명을 한 글자 이상 입력해주세요!", status: "error" });
    if (+subscriberCount <= 0) return toast({ title: "구독자 수를 0명 이상으로 입력해주세요!", status: "error" });
    const length = content.replace(/<[^>]*>?/g, "").length;
    if (length <= 20) {
      return toast({ title: `내용을 20자 이상 입력해주세요 ( ${length}/20 )`, status: "error" });
    }
    if (selectedNormalCategory === "-- 카테고리 선택 --") {
      return toast({ title: `일반 카테고리를 선택해주세요!`, status: "error" });
    }

    try {
      loading.start();

      await apiUpdatePromotionBoard({
        promotionBoardId: boardId,
        title,
        link,
        channelName,
        subscriberCount: +subscriberCount,
        tags: selectedTags.map((tag) => ({ tagName: tag })),
        categoryName: selectedNormalCategory,
        content,
      });

      queryClient.invalidateQueries(["promotionBoard", boardId + ""]);

      toast({ title: "게시글 수정했습니다.\n수정된 게시글 페이지로 이동됩니다.", status: "success" });

      router.replace(`/promotion/${boardId}`);
    } catch (error) {
      console.error(error);

      return toast({ title: "에러가 발생했습니다.\n잠시후에 다시 시도해주세요!", status: "error" });
    } finally {
      loading.end();
    }
  };

  const [isFocus, setIsFocus] = useState(false);

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
          <Input
            isEssential
            name="유튜브 링크"
            type="text"
            placeholder="유튜브 링크을 입력해주세요!"
            defaultValue={data?.link}
          />
          <div className="flex flex-col md:flex-row md:space-x-4">
            <Input
              isEssential
              name="채널명"
              type="text"
              placeholder="채널명을 입력해주세요!"
              defaultValue={data?.channelName}
            />
            <Input
              isEssential
              name="구독자 수"
              type="number"
              placeholder="구독자 수를 입력해주세요!"
              defaultValue={data?.subscriberCount}
            />
          </div>
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0">
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
            <NormalCategory selectedCategory={selectedNormalCategory} setSelectedCategory={setSelectedNormalCategory} />
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
