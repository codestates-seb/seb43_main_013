// type
import type { Metadata } from "next";
import SearchLists from "./SearchLists";
import { apiSSRFetchSearchBoard } from "@/apis/ssr";
import { getMetadata } from "@/libs";

/** 2023/05/05 - 메타데이터 - by 1-blue */
export const generateMetadata = async ({ searchParams: { keyword } }: Props): Promise<Metadata> => {
  const initialData = await apiSSRFetchSearchBoard({ keyword, page: 0, size: 10 });

  return getMetadata({
    title: initialData?.data?.[0]?.title || "검색 결과 없음",
    description:
      initialData?.data?.[0]?.content?.replace(/<[^>]*>?/g, "") || "검색된 게시글이나 유저가 존재하지 않습니다.",
  });
};

// type
interface Props {
  params: {};
  searchParams: { keyword: string };
}

/** 2023/05/22 - 검색된 페이지 - by 1-blue */
const Page = async ({ params, searchParams: { keyword } }: Props) => {
  const initialData = await apiSSRFetchSearchBoard({ keyword, page: 0, size: 10 });

  return (
    <>
      <h1 className="mt-12 text-center text-2xl text-sub-900">( 검색어: "{keyword}" )</h1>

      <SearchLists keyword={keyword} initialData={initialData} />
    </>
  );
};

export default Page;
