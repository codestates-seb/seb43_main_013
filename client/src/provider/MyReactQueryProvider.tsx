import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

/** 2023/05/04 - 전역 설정 적용 - by 1-blue */
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 10, // 10 분
      cacheTime: 1000 * 60 * 15, // 15 분
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
