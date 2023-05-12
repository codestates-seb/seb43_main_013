import { twMerge } from "tailwind-merge";

interface Props {
  className?: string;
}

/** 2023/05/12 - 텍스트 스켈레톤 UI 컴포넌트 - by 1-blue */
const Text: React.FC<Props> = ({ className }) => {
  return <div className={twMerge("rounded-md w-full h-6 animate-skeleton-gradient", className)} />;
};

export default Text;
