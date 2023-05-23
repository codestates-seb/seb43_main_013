"use client";

import SortPosts from "@/components/BoardMain/SortPosts";
import Pagination from "@/components/Pagination";
import RightSideButton from "@/components/RightSideButton";
import NoDataExists from "@/components/Svg/NoDataExists";
import { useFetchNoticeBoardList } from "@/hooks/query/useFetchNoticeBoardList";
import { usePageStore, useSortStore } from "@/store";
import { useMemberStore } from "@/store/useMemberStore";
import NoticeContentItem from "./NoticeContetnItem";

/** 2023/05/20 - 공지사항 메인 화면 - by leekoby */
const NoticeMain = () => {
  /** 2023/05/20 - 게시판 page 상태관리 - by leekoby */
  const currentPage = usePageStore((state) => state.currentPage);
  const setCurrentPage = usePageStore((state) => state.setCurrentPage);
  const member = useMemberStore((state) => state.member);
  /** 2023/05/20 - 정렬 전역 상태 - by leekoby */
  const sortSelectedOption = useSortStore((state) => state.selectedOption);
  console.log(sortSelectedOption);
  /** 2023/05/11  공지사항 목록 get 요청 - by leekoby */
  const { data, refetch } = useFetchNoticeBoardList({
    sorted: sortSelectedOption?.optionName,
    page: currentPage,
    size: 10,
  });

  return (
    // 전체 컨테이너
    <div className="mx-auto mt-6">
      <h2 className="text-2xl font-bold text-left"> 공지사항 </h2>
      <div className="flex justify-end  mb-4">
        <SortPosts />
      </div>
      <div className="flex flex-col md:flex-row ">
        {/* 카테고리 영역 추가 시  */}
        {/* <aside className=" flex flex-row md:flex-col items-center justify-center md:justify-start  md:w-0 md:grow-[2]  ">
        {categories && <SideCategories selectedCategory={selectedCategory} categories={categories} />}
      </aside> */}
        <section className="flex flex-col md:w-0 ml-5 grow-[8]">
          {/* freeboard list header */}

          {/* post item */}
          <div className="space-y-5">
            {data?.pages[0].data.length === 0 ? (
              <NoDataExists />
            ) : (
              data?.pages.map((page) =>
                page.data.map((innerData) => <NoticeContentItem props={innerData} key={innerData.noticeId} />),
              )
            )}
            {/* postslist bottom */}
            {data && (
              <div className="flex justify-center items-center">
                {/* TODO: React Query를 이용한 PreFetch 방식으로 변경하기 */}
                <Pagination
                  page={data?.pages[0].pageInfo.page}
                  totalPages={data?.pages[0].pageInfo.totalPages}
                  onPageChange={setCurrentPage}
                />
              </div>
            )}
          </div>
        </section>
        {/* 오른쪽 사이드 영역 */}
        {member?.email === process.env.NEXT_PUBLIC_ADMIN && (
          <div className="fixed right-0 bottom-0 transform -translate-y-1/2 ml-2">
            <RightSideButton destination={`/notice/write`} />
          </div>
        )}
      </div>
    </div>
  );
};

export default NoticeMain;
