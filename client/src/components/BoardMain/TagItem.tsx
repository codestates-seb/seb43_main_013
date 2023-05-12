"use client";

/** 2023/05/08 - 임시 Tag UI - by leekoby */
interface TagItemProps {
  tag: string;
}
const TagItem: React.FC<TagItemProps> = ({ tag }) => {
  return (
    <div className="bg-gray-200 rounded-2xl px-[10px] py-[5px] text-[10px] font-bold align-middle leading-3">{tag}</div>
  );
};

export default TagItem;
