import { useEffect, useState } from "react";
import { Combobox, Transition } from "@headlessui/react";
import { CheckIcon } from "@heroicons/react/24/solid";
import { twMerge } from "tailwind-merge";

// hook
import { useFetchCategories } from "@/hooks/query";

// type
import type { CategoryType } from "@/types/api";
interface Props {
  type: CategoryType;
  selectedCategory: string;
  setSelectedCategory: React.Dispatch<React.SetStateAction<string>>;
}

/** 2023/05/08 - category 컴포넌트 - by 1-blue */
const NormalCategory: React.FC<Props> = ({ type, selectedCategory, setSelectedCategory }) => {
  /** 2023/05/09 - 카테고리들 얻기 - by 1-blue */
  const { categories } = useFetchCategories({ type });

  /** 2023/05/09 - 카테고리 초깃값 - by 1-blue */
  useEffect(() => {
    if (selectedCategory) return;
    if (!categories) return;

    setSelectedCategory(categories[0]);
  }, [selectedCategory, categories, setSelectedCategory]);

  const [query, setQuery] = useState("");

  const filteredCategories =
    query === "" ? categories : categories?.filter((category) => category.toLowerCase().includes(query.toLowerCase()));

  let title = "";
  if (type === "normal") title = "전체";
  if (type === "feedback") title = "피드백";
  if (type === "job") title = "구인구직";

  return (
    <section className={twMerge("relative flex-1 flex flex-col min-w-0", type === "normal" && "z-[1]")}>
      <span className="text-base font-bold text-gray-800 mb-1">{title} 카테고리</span>
      <Combobox value={selectedCategory} onChange={setSelectedCategory}>
        {({ open }) => (
          <>
            <Combobox.Input
              onChange={(e) => setQuery(e.target.value)}
              className="px-3 py-1 bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold"
            />
            <Transition
              show={open}
              enter="transition duration-150 ease-out"
              enterFrom="transform scale-90 opacity-0"
              enterTo="transform scale-100 opacity-100"
              leave="transition duration-150 ease-out"
              leaveFrom="transform scale-100 opacity-100"
              leaveTo="transform scale-90 opacity-0"
            >
              <Combobox.Options className="absolute bottom-0 w-full translate-y-full shadow-md rounded-b-md overflow-hidden">
                {filteredCategories?.map((category) => (
                  <Combobox.Option key={category} value={category}>
                    {({ active, selected }) => (
                      <li
                        className={twMerge(
                          "group flex p-2 space-x-0.5 cursor-pointer hover:bg-main-500 hover:text-white hover:font-bold focus:bg-main-500",
                          active ? "bg-main-500 text-white font-bold" : "bg-white text-black",
                        )}
                      >
                        {selected ? (
                          <CheckIcon
                            className={twMerge(
                              "w-5 h-5 text-main-500 group-hover:text-white",
                              active ? "text-white" : "",
                            )}
                          />
                        ) : (
                          <div className="w-5 h-5" />
                        )}
                        <span>{category}</span>
                      </li>
                    )}
                  </Combobox.Option>
                ))}
              </Combobox.Options>
            </Transition>
          </>
        )}
      </Combobox>
    </section>
  );
};

export default NormalCategory;
