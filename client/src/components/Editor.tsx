"use client";

import { useMemo } from "react";
import dynamic from "next/dynamic";

import "react-quill/dist/quill.snow.css";

const ReactQuill = dynamic(() => import("react-quill"), {
  ssr: false,
  // layout shift 방지
  loading: () => <div className="min-h-[calc(50vh+42px)] max-h-[calc(80vh+42px)] bg-main-100" />,
});

// type
import type ReactQuillType from "react-quill";

/** 2023/05/08 - react-quill에서 사용되는 tool format - by 1-blue */
const formats: ReactQuillType.ReactQuillProps["formats"] = [
  "font",
  "header",
  "bold",
  "italic",
  "underline",
  "strike",
  "blockquote",
  "code-block",
  "formula",
  "list",
  "bullet",
  "indent",
  "link",
  "image",
  "video",
  "align",
  "color",
  "background",
];

// type
interface Props {
  content: string;
  setContent: React.Dispatch<React.SetStateAction<string>>;
}

/** 2023/05/09 - react-quill을 이용한 wysiwyg - by 1-blue */
const Editor: React.FC<Props> = ({ content, setContent }) => {
  /** 2023/05/08 - react-quill에서 사용되는 tool modules - by 1-blue */
  const modules: ReactQuillType.ReactQuillProps["modules"] = useMemo(
    () => ({
      toolbar: {
        // container에 등록되는 순서대로 tool 배치
        container: [
          // font
          [{ font: [] }],
          // header
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          // 굵기, 기울기, 밑줄, 가운데라인, 인용, 코드블럭, 수학기호
          ["bold", "italic", "underline", "strike", "blockquote", "code-block", "formula"],
          // 리스트, 인덴트
          [{ list: "ordered" }, { list: "bullet" }, { indent: "-1" }, { indent: "+1" }],
          // 링크, 이미지, 비디오
          ["link", "image", "video"],
          // 정렬, 글자색, 배경색
          [{ align: [] }, { color: [] }, { background: [] }],
          // 초기화
          ["clean"],
        ],
      },
    }),
    [],
  );

  return (
    <ReactQuill
      theme="snow"
      formats={formats}
      modules={modules}
      value={content}
      placeholder="내용을 입력하세요."
      onChange={(content, delta, source, editor) => setContent(content)}
      className="editor"
    />
  );
};

export default Editor;
