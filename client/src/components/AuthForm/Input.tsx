import { useRef, useEffect } from "react";
import { twMerge } from "tailwind-merge";

interface Props extends React.DetailedHTMLProps<React.InputHTMLAttributes<HTMLInputElement>, HTMLInputElement> {
  error?: string;
}

/** 2023/05/20 - 로그인/회원가입에 사용할 input - by 1-blue */
const Input: React.FC<Props> = ({ type, name, placeholder, error }) => {
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (!error) return;

    inputRef.current?.select();
  }, [error]);

  return (
    <div className="flex flex-col">
      <label htmlFor={name} className="cursor-pointer">
        {name}
      </label>
      <input
        type={type}
        id={name}
        name={name}
        ref={inputRef}
        placeholder={placeholder}
        className={twMerge(
          "bg-transparent border-2 rounded-md px-2 py-1 transition-colors placeholder:text-sm placeholder:text-gray-500 focus:outline-none focus:border-main-500",
          !error && "mb-4",
        )}
      />
      {error && <span className="ml-auto mt-1.5 text-xs text-red-600">{error}</span>}
    </div>
  );
};

export default Input;
