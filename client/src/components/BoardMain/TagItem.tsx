"use client";

/** 2023/05/08 - 임시 Tag UI - by leekoby */
interface TagItemProps {
  tag: string;
}
const TagItem: React.FC<TagItemProps> = ({ tag }) => {
  return (
    <li>
      <button
        type="button"
        className="px-3 py-1 bg-main-400 text-sm text-white rounded-sm transition-colors hover:font-bold hover:bg-main-500"
      >
        {tag}
      </button>
    </li>
  );
};

export default TagItem;
