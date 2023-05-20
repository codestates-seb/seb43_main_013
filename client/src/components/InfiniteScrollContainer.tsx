import { useCallback, useEffect, useRef } from "react";

// type
import type { PropsWithChildren } from "react";
interface Props {
  hasMore?: boolean;
  fetchMore: () => void;
}

/** 2023/05/18 - "IntersectionObserver"의 옵션들 - by 1-blue */
const options: IntersectionObserverInit = {
  threshold: 0.1,
};

/** 2023/05/18 - 무한 스크롤링 적용 ( using "IntersectionObserver" ) - by 1-blue */
const InfiniteScrollContainer: React.FC<PropsWithChildren<Props>> = ({ hasMore, fetchMore, children }) => {
  /** 2023/05/18 - 감시할 요소의 ref - by 1-blue */
  const observerRef = useRef<HTMLDivElement>(null);

  /** 2023/05/18 - 상단으로 뷰포트 내에 감시하는 태그가 들어왔다면 패치 - by 1-blue */
  const onScroll: IntersectionObserverCallback = useCallback(
    (entries) => {
      if (!entries[0].isIntersecting) return;

      fetchMore();
    },
    [fetchMore],
  );
  /** 2023/05/18 - 상단 observer 등록 ( 해당 태그가 뷰포트에 들어오면 게시글 추가 패치 실행 ) - by 1-blue */
  useEffect(() => {
    if (!observerRef.current) return;

    // 콜백함수와 옵션값 지정
    let observer = new IntersectionObserver(onScroll, options);

    // 특정 요소 감시 시작
    observer.observe(observerRef.current);

    // 더 가져올 게시글이 존재하지 않는다면 패치 중지
    if (!hasMore) observer.unobserve(observerRef.current);

    // 감시 종료
    return () => observer.disconnect();
  }, [observerRef, onScroll, hasMore]);

  return (
    <>
      {children}
      <div ref={observerRef} style={{ paddingTop: "1px" }} />
    </>
  );
};

export default InfiniteScrollContainer;
