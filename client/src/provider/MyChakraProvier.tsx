import { CacheProvider } from "@chakra-ui/next-js";
import { ChakraProvider } from "@chakra-ui/react";

/** 2023/05/04 - chakra-ui provider 컴포넌트 - by 1-blue */
const MyChakraProvier: React.FC<React.PropsWithChildren> = ({ children }) => {
  return (
    <CacheProvider>
      <ChakraProvider>{children}</ChakraProvider>
    </CacheProvider>
  );
};

export default MyChakraProvier;
