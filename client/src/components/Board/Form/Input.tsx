// type
interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  name: string;
  message?: string;
  noMessage?: boolean;
}

/** 2023/05/08 - 게시글 생성에 사용하는 Input component - by 1-blue */
const Input: React.FC<Props> = ({ name, message, noMessage, ...rest }) => (
  <label htmlFor={name} className="flex-1 w-full flex flex-col">
    <span className="text-base font-bold text-sub-800 mb-1">{name}</span>
    <input
      id={name}
      name={name}
      {...rest}
      className="px-3 py-1 bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold"
    />
    {noMessage || <span className="text-right text-xs text-red-500">{message ? message : "ㅤ"}</span>}
  </label>
);

export default Input;
