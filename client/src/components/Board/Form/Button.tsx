import { twMerge } from "tailwind-merge";

// type
interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {}

/** 2023/05/15 - Button component - by 1-blue */
const Button: React.FC<React.PropsWithChildren<Props>> = ({ children, className, ...rest }) => (
  <button
    {...rest}
    className={twMerge(
      "ml-auto px-4 py-2 bg-main-400 font-bold text-white transition-colors rounded-md hover:bg-main-500 active:bg-main-600 active:ring-2 active:ring-offset-2 active:ring-main-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-main-500",
      className,
    )}
  >
    {children}
  </button>
);

export default Button;
