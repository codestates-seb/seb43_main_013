import Link from "next/link";
import { useCategoriesStore, usePageStore, useSortStore, useFeedbackCategoriesStore } from "@/store";
import { usePathname } from "next/navigation";

const link = [
  {
    name: "자유",
    link: "/free",
  },
  {
    name: "피드백",
    link: "/feedback",
  },
  {
    name: "홍보",
    link: "/promotion",
  },
  {
    name: "구인",
    link: "/job",
  },
];

/** 2023/05/11 - 내이베이션바 라우팅 컴포넌트 - by Kadesti */
const NavRoute = () => {
  /** 2023/05/17 - 페이지 정보 초기화 - by leekoby */
  /** 2023/05/14 - 다른 링크로 이동할때 페이지 정보 초기화 - by leekoby */
  const resetPageState = usePageStore((state) => state.resetPageState);
  /** 2023/05/14 - 다른 링크로 이동할때 사이드 카테고리 정보 초기화 - by leekoby */
  const resetCategoryState = useCategoriesStore((state) => state.resetCategoryState);
  /** 2023/05/15 - 다른 링크로 이동할때 정렬 정보 초기화 - by leekoby */
  const resetSelectedOption = useSortStore((state) => state.resetSelectedOption);
  /** 2023/05/15 - 다른 링크로 이동할때 피드백 카테고리 정보 초기화 - by leekoby */
  const resetFeedbackCategories = useFeedbackCategoriesStore((state) => state.resetFeedbackCategoryState);

  const handleResetClick = () => {
    resetPageState();
    resetCategoryState();
    resetSelectedOption();
    resetFeedbackCategories();
  };
  const pathname = usePathname();

  return (
    <ul className="flex flex-col px-3 md:flex-row">
      {link.map((item) => {
        const { name, link } = item;
        const actStyle = pathname.includes(link) ? "text-main-400 border-b-2 border-b-main-400" : "";

        return (
          <Link href={link} key={link} className="md:flex md:items-center">
            <li
              onClick={handleResetClick}
              className={`cursor-pointer text-xl text-black hover:text-main-400 hover:border-b-2 hover:border-b-main-400 h-8 md:mr-14 mb-4 md:mb-0 ${actStyle}`}
            >
              {name}
            </li>
          </Link>
        );
      })}
    </ul>
  );
};

export default NavRoute;
