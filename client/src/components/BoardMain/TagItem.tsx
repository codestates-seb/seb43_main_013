"use client";

/** 2023/05/08 - 임시 Tag UI - by leekoby */
interface TagItemProps {
  tag: string;
}
const TagItem: React.FC<TagItemProps> = ({ tag }) => {
  return (
    <li className="list-none">
      <button
        type="button"
        className="px-3 py-1 bg-main-400 text-sm text-white rounded-lg transition-colors hover:font-bold hover:bg-main-500"
      >
        {tag}
      </button>
    </li>
  );
};

export default TagItem;
