"use client";

import { DetailTag } from "@/types/api";
import Link from "next/link";

/** 2023/05/08 - 임시 Tag UI - by leekoby */
interface TagItemProps {
  tags: DetailTag[];
}
const TagItem: React.FC<TagItemProps> = ({ tags }) => {
  return (
    <ul className="flex space-x-2 flex-wrap">
      {tags.map(({ tagName }) => (
        <li key={tagName}>
          <Link
            href={`/search?keyword=${tagName}`}
            className="px-2 py-1 mt-1 text-xs font-bold text-main-500 border-2 border-main-500 rounded-lg transition-colors hover:bg-main-500 hover:text-white"
          >
            {tagName}
          </Link>
        </li>
      ))}
    </ul>
  );
};

export default TagItem;
