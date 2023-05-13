import Skeleton from ".";

interface Props {
  count: number;
}

/** 2023/05/12 - 댓글 스켈레톤 UI 컴포넌트 - by 1-blue */
const Comment: React.FC<Props> = ({ count }) => (
  <ul className="flex flex-col space-y-4">
    {Array(count)
      .fill(null)
      .map((v, i) => (
        <li key={i} className="flex space-x-3">
          <Skeleton.Circle />
          <div className="flex-1 space-y-2">
            <div className="flex space-x-2">
              <Skeleton.Square className="w-20 h-5" />
              <Skeleton.Square className="w-20 h-5" />
              <Skeleton.Square className="w-8 h-5" />
              <Skeleton.Square className="w-8 h-5" />
            </div>
            <div className="space-y-1">
              <Skeleton.Text />
              <Skeleton.Text />
            </div>

            <Skeleton.Square className="h-5" />
          </div>
        </li>
      ))}
  </ul>
);

export default Comment;
