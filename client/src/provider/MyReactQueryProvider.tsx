import { useToast } from "@chakra-ui/react";
import { isAxiosError } from "axios";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

/** 2023/05/04 - 에러 처리 핸들러 - by 1-blue */
const queryErrorHandler = (error: unknown) => {
  const toast = useToast();

  let message = "알 수 없는 오류가 발생했습니다.";

  // Axios Error
  if (isAxiosError(error)) {
    message = error.response?.data.message;
  }
  // Error
  else if (error instanceof Error) {
    message = error.message;
  }

  toast({
    description: message,
    status: "warning",
    duration: 2000,
    isClosable: true,
  });
};

// /** 2023/05/04 - 성공 처리 핸들러 - by 1-blue */
// const querySuccessHandler = (data: unknown) => {
//   const toast = useToast();

//   if (typeof data === "object" && data && "message" in data && typeof data.message === "string") {
//     toast({
//       description: data.message,
//       status: "success",
//       duration: 2000,
//       isClosable: true,
//     });
//   }
// };

/** 2023/05/04 - 전역 설정 적용 - by 1-blue */
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      onError: queryErrorHandler,
      staleTime: 1000 * 60 * 10, // 10 분
      cacheTime: 1000 * 60 * 15, // 15 분
    },
    mutations: {
      onError: queryErrorHandler,
    },
  },
});

/** 2023/05/04 - "react-query" Provider 적용 - by 1-blue */
const MyReactQueryProvider: React.FC<React.PropsWithChildren> = ({ children }) => (
  <QueryClientProvider client={queryClient}>
    <ReactQueryDevtools initialIsOpen position="top-left" />

    {children}
  </QueryClientProvider>
);

export default MyReactQueryProvider;
