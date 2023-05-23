import { useCallback } from "react";

import type { FreeBoard } from "@/types/api";
import Link from "next/link";
interface Props extends Pick<FreeBoard, "content"> {}

/** 2023/05/24 - 특정 게시글의 해시태그 - by 1-blue */
const BoardHashtag: React.FC<Props> = ({ content }) => {
  // 2023/05/23 - 문장에 해시태그 존재하면 링크 처리 - by 1-blue
  const preprocessHashtag = useCallback(
    (content: string) => [...new Set(content.split(/(#[^\s<>#]+)/gm).filter((text) => text[0] === "#"))],
    [],
  );

  const hashtags = preprocessHashtag(content);

  return (
    <ul className="flex space-x-4 font-semibold">
      {hashtags.map((hashtag) => (
        <li key={hashtag}>
          <Link
            href={`/search?keyword=${hashtag.slice(1)}`}
            className="text-lg text-main-400 transition-colors hover:text-main-500"
          >
            {hashtag}
          </Link>
        </li>
      ))}
    </ul>
  );
};

export default BoardHashtag;
