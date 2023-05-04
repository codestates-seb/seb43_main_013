"use client";

// type
interface Props {
  error: Error;
  reset: () => void;
}

/** 2023/05/04 - Erorr 페이지 - by 1-blue */
const Error: React.FC<Props> = ({ error, reset }) => {
  return <div>에러 페이지...</div>;
};

export default Error;
