import { useEffect } from "react";

// hook
import useResizeTextarea from "@/hooks/useResizeTextarea";
import { twMerge } from "tailwind-merge";

// type
interface Props extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  name: string;
  message?: string;
  noMessage?: boolean;
}

/** 2023/05/15 - Textarea component - by 1-blue */
const Textarea: React.FC<Props> = ({ name, message, noMessage, className, ...rest }) => {
  const [textRef, handleResizeHeight] = useResizeTextarea();

  /** 2023/05/15 - 초기 사이즈 지정 - by 1-blue */
  useEffect(handleResizeHeight, [handleResizeHeight]);

  return (
    <label htmlFor={name} className="flex-1 w-full flex flex-col">
      <span className="text-base font-bold text-gray-800 mb-1">{name}</span>
      <textarea
        id={name}
        name={name}
        ref={textRef}
        onChange={handleResizeHeight}
        {...rest}
        className={twMerge(
          "px-3 py-1 bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold",
          className,
        )}
      />
      {noMessage || <span className="text-right text-xs text-red-500">{message ? message : "ㅤ"}</span>}
    </label>
  );
};

export default Textarea;
