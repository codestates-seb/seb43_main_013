"use client";

import MyChakraProvier from "./MyChakraProvier";
import MyReactQueryProvider from "./MyReactQueryProvider";

/** 2023/05/04 - provider 컴포넌트 - by 1-blue */
const Provider: React.FC<React.PropsWithChildren> = ({ children }) => {
  return (
    <MyChakraProvier>
      <MyReactQueryProvider>{children}</MyReactQueryProvider>
    </MyChakraProvier>
  );
};

export default Provider;
