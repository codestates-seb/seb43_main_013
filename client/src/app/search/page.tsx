// type
import type { Metadata } from "next";
import SearchLists from "./SearchLists";

/** 2023/05/05 - 메타데이터 - by 1-blue */
export const generateMetadata = async ({ searchParams: { keyword } }: Props): Promise<Metadata> => {
  const result = await fetch(process.env.NEXT_PUBLIC_BASE_URL + `/api/search?keyword=${keyword}`, {
    method: "GET",
    cache: "no-cache",
  }).then((res) => res.json());

  return {
    title: `CC | 검색 ( ${keyword} )`,
    description: `"${keyword}"를 검색한 결과 페이지입니다.`,
  };
};

// type
interface Props {
  params: {};
  searchParams: { keyword: string };
}

/** 2023/05/22 - 검색된 페이지 - by 1-blue */
const Page = async ({ params, searchParams: { keyword } }: Props) => {
  const result = await fetch(process.env.NEXT_PUBLIC_BASE_URL + `/api/search?keyword=${keyword}`, {
    method: "GET",
    cache: "no-cache",
  }).then((res) => res.json());

  return (
    <>
      <SearchLists keyword={keyword} initialData={result} />
    </>
  );
};

export default Page;
