import { useEffect, useState } from "react";
import { Combobox, Transition } from "@headlessui/react";
import { CheckIcon, ChevronUpDownIcon } from "@heroicons/react/24/solid";
import { twMerge } from "tailwind-merge";

// hook
import { useFetchJobCategories } from "@/hooks/query";

// type
interface Props {
  selectedCategory: string;
  setSelectedCategory: React.Dispatch<React.SetStateAction<string>>;
}

/** 2023/05/18 - 구인구직 category 컴포넌트 - by 1-blue */
const JobCategory: React.FC<Props> = ({ selectedCategory, setSelectedCategory }) => {
  /** 2023/05/18 - 카테고리들 얻기 - by 1-blue */
  const { jobCategories } = useFetchJobCategories({ type: "job" });

  /** 2023/05/18 - 카테고리 초깃값 - by 1-blue */
  useEffect(() => {
    if (selectedCategory) return;
    if (!jobCategories?.length) return;

    setSelectedCategory("-- 카테고리 선택 --");
  }, [selectedCategory, jobCategories, setSelectedCategory]);

  const [query, setQuery] = useState("");

  const filteredCategories =
    query === ""
      ? jobCategories
      : jobCategories?.filter(({ jobCategoryName }) => jobCategoryName.toLowerCase().includes(query.toLowerCase()));

  return (
    <section className={"relative flex-1 flex flex-col min-w-0 z-[11]"}>
      <span className="text-base font-bold text-sub-800 mb-1">구인구직 카테고리</span>
      <Combobox value={selectedCategory} onChange={setSelectedCategory}>
        {({ open }) => (
          <>
            <div className="relative w-full cursor-default overflow-hidden text-left shadow-md focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 focus-visible:ring-offset-2 focus-visible:ring-offset-teal-300 sm:text-sm">
              <Combobox.Input
                onChange={(e) => setQuery(e.target.value)}
                className="px-3 py-1 w-full bg-transparent rounded-sm text-lg border-2 border-main-300 focus:outline-none focus:border-main-500 placeholder:text-sm placeholder:font-bold"
                onClick={(e) => {
                  if (!(e.target instanceof HTMLInputElement)) return;

                  e.target.select();
                }}
              />
              <Combobox.Button className="absolute inset-y-0 right-0 flex items-center pr-2">
                <ChevronUpDownIcon
                  className="h-8 w-8 text-sub-400 transition-colors hover:text-sub-600"
                  aria-hidden="true"
                />
              </Combobox.Button>
            </div>
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
                {filteredCategories
                  ?.filter((category) => category.jobCategoryName !== "전체")
                  .map((category) => (
                    <Combobox.Option key={category.jobCategoryId} value={category.jobCategoryName}>
                      {({ active, selected }) => (
                        <li
                          className={twMerge(
                            "group flex p-2 space-x-2.5 cursor-pointer hover:bg-main-500 hover:text-white hover:font-bold focus:bg-main-500",
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
                          <span>{category.jobCategoryName}</span>
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

export default JobCategory;
