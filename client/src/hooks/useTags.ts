import { useCallback, useState } from "react";
import useCustomToast from "./useCustomToast";

/** 2023/05/09 - 게시글 작성 시 사용하는 태그 훅 - by 1-blue */
const useTags = (ininitalTags: string[] = []) => {
  const toast = useCustomToast();

  /** 2023/05/09 - 선택한 태그들 - by 1-blue */
  const [selectedTags, setSelectedTags] = useState(ininitalTags);

  /** 2023/05/09 - 태그 추가 - by 1-blue */
  const onSelectedTag: React.KeyboardEventHandler<HTMLInputElement> = useCallback(
    (e) => {
      // 엔터키 확인
      if (e.key !== "Enter") return;

      const { target } = e;

      // submit 방지
      e.preventDefault();

      // input 확인
      if (!(target instanceof HTMLInputElement)) return;

      // 태그가 없는 경우
      if (target.value.trim().length === 0) {
        target.value = "";

        return toast({ title: "태그를 작성해주세요!", status: "warning" });
      }
      // 태그가 10개 이상인 경우
      if (selectedTags.length > 10) {
        target.value = "";

        return toast({ title: "태그는 최대 10개까지 생성 가능합니다!", status: "warning" });
      }
      // 태그가 이미 존재하는 경우
      if (selectedTags.some((selectedTag) => target.value === selectedTag)) {
        target.value = "";

        return toast({ title: "이미 존재하는 태그입니다!", status: "warning" });
      }

      const tag = target.value.trim();

      // 태그 추가
      setSelectedTags((tags) => [...tags, tag]);

      // 리셋
      target.value = "";

      toast({ title: "태그를 추가했습니다.", status: "success" });
    },
    [selectedTags],
  );

  /** 2023/05/09 - 태그 제거 ( 버블링 ) - by 1-blue */
  const onDeleteTag: React.MouseEventHandler<HTMLUListElement> = ({ target }) => {
    if (!(target instanceof HTMLButtonElement)) return;
    if (!target.dataset.tag) return;

    setSelectedTags((selectedTags) => selectedTags.filter((selectedTag) => selectedTag !== target.dataset.tag));

    toast({ title: "태그를 제거했습니다.", status: "success" });
  };

  return [selectedTags, onSelectedTag, onDeleteTag, setSelectedTags] as const;
};

export default useTags;
