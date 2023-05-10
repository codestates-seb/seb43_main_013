// type
interface Props {
  selectedTag: string;
}

/** 2023/05/09 - 게시글 생성 폼에서 사용할 태그 - by 1-blue */
const Tag: React.FC<Props> = ({ selectedTag }) => (
  <li>
    <button
      type="button"
      className="px-3 py-1 bg-main-400 text-sm text-white rounded-sm transition-colors hover:font-bold hover:bg-main-500"
      data-tag={selectedTag}
    >
      {selectedTag}
    </button>
  </li>
);

export default Tag;
