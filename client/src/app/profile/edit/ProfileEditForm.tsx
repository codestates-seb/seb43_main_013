"use client";

import { useState } from "react";

// component
import Form from "@/components/Board/Form";

/** 2023/05/15 - 프로필 수정 폼 - by 1-blue */
const ProfileEditForm = () => {
  const [thumbnail, setThumbnail] = useState<FileList | null>(null);

  /** 2023/05/15 - 프로필 수정 요청 - by 1-blue */
  const onSumbit: React.FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    const values: string[] = [];
    const formData = new FormData(e.currentTarget);

    for (let value of formData.values()) {
      if (typeof value !== "string") continue;

      values.push(value);
    }

    const [nickname, phone, link, introduction] = values;

    // TODO: 유효성 검사
    // TODO: 프로필 수정 요청
  };

  return (
    <form
      onSubmit={onSumbit}
      className="flex-1 flex flex-col border-r px-8 pt-4 pb-8 space-y-2 bg-white shadow-2xl m-4 rounded-lg"
    >
      <Form.Photo setThumbnail={setThumbnail} className="h-[320px]" />
      <Form.Input name="닉네임" placeholder="ex) 1-blue" />
      <Form.Input name="휴대폰 번호" placeholder="ex) 010-1234-5678" />
      <Form.Input name="유튜브 링크" placeholder="ex) https://www.youtube.com/@15ya.fullmoon" />
      <Form.Textarea
        name="자기소개"
        placeholder={
          "안녕하세요 새싹 유튜버 **입니다.\n많은 조언 해주시면 감사하겠습니다.\n유튜브 링크: https://www.youtube.com/@15ya.fullmoon"
        }
        className="min-h-[20vh] resize-none overflow-hidden"
      />
      <Form.Button>수정</Form.Button>
    </form>
  );
};

export default ProfileEditForm;
