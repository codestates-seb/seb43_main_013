"use client";

import { useMemo } from "react";
import dynamic from "next/dynamic";

import "react-quill/dist/quill.snow.css";

// @ts-ignore
const ReactQuill = dynamic(() => import("react-quill"), { ssr: false });

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

const Editor: React.FC<Props> = ({ content, setContent }) => {
  // /** 2023/05/08 - 이미지 업로드 핸들러 - by 1-blue */
  // const imageHandler = () => {
  //   // file input 임의 생성
  //   const input = document.createElement("input");
  //   input.setAttribute("type", "file");
  //   input.click();

  //   input.onchange = async () => {
  //     const file = input.files;
  //     const formData = new FormData();

  //     if (file) {
  //       formData.append("multipartFiles", file[0]);
  //     }

  //     console.log("file >> ", file);

  //     // // file 데이터 담아서 서버에 전달하여 이미지 업로드
  //     // const res = await axios.post("http://localhost:8080/uploadImage", formData);

  //     // if (quillRef.current) {
  //     //   // 현재 Editor 커서 위치에 서버로부터 전달받은 이미지 불러오는 url을 이용하여 이미지 태그 추가
  //     //   const index = (quillRef.current.getEditor().getSelection() as RangeStatic).index;

  //     //   const quillEditor = quillRef.current.getEditor();
  //     //   quillEditor.setSelection(index, 1);

  //     //   quillEditor.clipboard.dangerouslyPasteHTML(index, `<img src=${res.data} alt=${"alt text"} />`);
  //     // }
  //   };
  // };

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
        handlers: {
          // image: imageHandler,
        },
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
    />
  );
};

export default Editor;
