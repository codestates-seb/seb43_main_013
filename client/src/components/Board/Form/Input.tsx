// type
interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  name: string;
  message?: string;
  noMessage?: boolean;
  inputRef?: React.RefObject<HTMLInputElement>;
  isEssential?: boolean;
}

/** 2023/05/08 - 게시글 생성에 사용하는 Input component - by 1-blue */
const Input: React.FC<Props> = ({ name, message, inputRef, noMessage, isEssential, ...rest }) => (
  <label htmlFor={name} className="flex-1 w-full flex flex-col cursor-pointer">
    <span className="text-base font-bold text-sub-800 mb-1">
      {name}
      {isEssential && " *"}
    </span>
    <input
      id={name}
      name={name}
      ref={inputRef}
      {...rest}
      className="px-3 py-1 bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold shadow-sm focus:shadow-md"
    />
    {noMessage || <span className="text-right text-xs text-red-500">{message ? message : "ㅤ"}</span>}
  </label>
);

export default Input;
