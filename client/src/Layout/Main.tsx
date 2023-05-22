/** 2023/05/04 - 메인 컴포넌트 - by 1-blue */
const Main: React.FC<React.PropsWithChildren> = ({ children }) => {
  return <main className="max-w-[1440px] mx-auto">{children}</main>;
};

export default Main;
