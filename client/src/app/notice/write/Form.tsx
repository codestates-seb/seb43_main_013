"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import useCustomToast from "@/hooks/useCustomToast";

//api
import { apiCreateNoticeBoard } from "@/apis";

//store
import { useLoadingStore } from "@/store";

//hook
import { useMemberStore } from "@/store/useMemberStore";

//component
import Input from "@/components/Board/Form/Input";
import Editor from "@/components/Editor";

/** 2023/05/20 - 공지사항 게시글 작성 Form 컴포넌트 - by leekoby */
const Form = () => {
  const toast = useCustomToast();
  const router = useRouter();
  const { loading } = useLoadingStore((state) => state);
  const { member } = useMemberStore();

  /** 2023/05/20 - wysiwyg 으로 받는 content - by leekoby */
  const [content, setContent] = useState("");

  /** 2023/05/20 - 공지사항 게시글 생성 - by leekoby */
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
    if (content.trim().length <= 100) return toast({ title: "내용이 너무 적습니다!", status: "error" });

    try {
      loading.start();

      const { noticeId } = await apiCreateNoticeBoard({
        memberId: member.memberId,
        title,
        content,
      });

      toast({ title: "게시글 생성했습니다.\n생성된 게시글 페이지로 이동됩니다.", status: "success" });

      router.push(`/notice/${noticeId}`);
    } catch (error) {
      console.error(error);

      return toast({ title: "에러가 발생했습니다.\n잠시후에 다시 시도해주세요!", status: "error" });
    } finally {
      loading.end();
    }
  };

  return (
    <form className="flex flex-col space-y-4 px-4 p-8 bg-white shadow-lg m-4 mt-0 rounded-md" onSubmit={onSubmit}>
      {/* title */}
      <section className="flex space-y-4 md:space-y-0 md:space-x-4 z-[1] flex-col md:flex-row flex-1">
        {/* title, link,  */}
        <div className="w-full md:w-0 md:flex-1 space-y-2 z-[1]">
          <Input name="제목" type="text" placeholder="제목을 입력해주세요!" />
          <div className="flex flex-col md:flex-row space-y-4 md:space-x-4 md:space-y-0"></div>
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
        작성 완료
      </button>
    </form>
  );
};
export default Form;
