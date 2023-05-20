import Link from "next/link";
import { useCategoriesStore, usePageStore, useSortStore, useFeedbackCategoriesStore } from "@/store";
import { usePathname } from "next/navigation";
import { motion } from "framer-motion";
import { twMerge } from "tailwind-merge";

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
    <ul className="flex space-x-8 tracking-widest">
      {link.map((item) => {
        const { name, link } = item;

        return (
          <Link key={link} href={link} className="relative py-4 first:pl-0">
            <li
              onClick={handleResetClick}
              className={twMerge("text-xl font-sub", link === pathname && "text-main-300")}
            >
              {name}
            </li>
            {link === pathname && (
              <motion.div className="absolute bottom-0 w-full h-1 bg-main-200 rounded-sm" layoutId="nav" />
            )}
          </Link>
        );
      })}
    </ul>
  );
};

export default NavRoute;
