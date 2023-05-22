import { twMerge } from "tailwind-merge";

interface Props {
  className?: string;
}

/** 2023/05/12 - 사각형 스켈레톤 UI 컴포넌트 - by 1-blue */
const Square: React.FC<Props> = ({ className }) => {
  return <div className={twMerge("rounded-md w-12 h-6 animate-skeleton-gradient", className)} />;
};

export default Square;
