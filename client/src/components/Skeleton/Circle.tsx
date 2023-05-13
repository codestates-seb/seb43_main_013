import { twMerge } from "tailwind-merge";

interface Props {
  className?: string;
}

/** 2023/05/12 - 원형 스켈레톤 UI 컴포넌트 - by 1-blue */
const Circle: React.FC<Props> = ({ className }) => {
  return <div className={twMerge("rounded-full w-12 h-12 animate-skeleton-gradient", className)} />;
};

export default Circle;
