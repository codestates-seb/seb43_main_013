"use client";

import SortPosts from "@/components/BoardMain/SortPosts";
import Pagination from "@/components/Pagination";
import RightSideButton from "@/components/RightSideButton";
import FullSpinner from "@/components/Spinner/FullSpinner";
import { useFetchNoticeBoardList } from "@/hooks/query/useFetchNoticeBoardList";
import { usePageStore, useSortStore } from "@/store";
import NoticeContentItem from "./NoticeContetnItem";

/** 2023/05/20 - 공지사항 메인 화면 - by leekoby */
const NoticeMain = () => {
  /** 2023/05/20 - 게시판 page 상태관리 - by leekoby */
  const currentPage = usePageStore((state) => state.currentPage);
  const setCurrentPage = usePageStore((state) => state.setCurrentPage);

  /** 2023/05/20 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);

  /** 2023/05/11 자유게시판 목록 get 요청 - by leekoby */
  const { data, refetch } = useFetchNoticeBoardList({
    sorted: sortSelectedOption?.optionName,
    page: currentPage,
    size: 10,
  });

  if (!data) return <FullSpinner />;
  if (data.pages.length < 1) return <FullSpinner />;

  console.log("notice main  >>> ", data);

  return (
    // 전체 컨테이너
    <div className="mx-auto mt-6 min-w-min">
      <h1 className="text-3xl font-bold text-left"> 공지사항 </h1>
      <div className="flex flex-col md:flex-row ">
        {/* 카테고리 영역 추가 시  */}
        {/* <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
        {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}
      </aside> */}
        <section className="flex flex-col md:w-0 ml-5 grow-[8]">
          {/* freeboard list header */}
          <div className="flex justify-end  mb-4">
            <SortPosts />
          </div>

          {/* post item */}
          <div className="space-y-5">
            {data.pages.map((item) =>
              item.data.map((innerData) => (
                <div className="" key={innerData.noticeId}>
                  <NoticeContentItem props={innerData} />
                </div>
              )),
            )}

            {/* postslist bottom */}
            <div className="flex justify-center items-center">
              {/* TODO: React Query를 이용한 PreFetch 방식으로 변경하기 */}
              <Pagination
                page={data?.pages[0].pageInfo.page}
                totalPages={data?.pages[0].pageInfo.totalPages}
                onPageChange={setCurrentPage}
              />
            </div>
          </div>
        </section>
        {/* 오른쪽 사이드 영역 */}
        <div className="opacity-50 lg:opacity-100 fixed right-0 lg:top-1/2 transform -translate-y-1/2 ml-2">
          <RightSideButton destination={`/notice/write`} />
        </div>
      </div>
    </div>
  );
};

export default NoticeMain;
