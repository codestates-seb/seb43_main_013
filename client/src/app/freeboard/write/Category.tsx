import { useState } from "react";
import { Combobox, Transition } from "@headlessui/react";
import { CheckIcon } from "@heroicons/react/24/solid";

// lib
import { combineClassnames } from "@/libs";

// type
interface Props {
  categories: string[];
}

/** 2023/05/08 - category 컴포넌트 - by 1-blue */
const Category: React.FC<Props> = ({ categories }) => {
  const [selectedCategory, setSelectedCategory] = useState(categories[0]);
  const [query, setQuery] = useState("");

  const filteredCategories =
    query === "" ? categories : categories.filter((category) => category.toLowerCase().includes(query.toLowerCase()));

  return (
    <section className="relative flex flex-col">
      <span className="text-base font-bold text-gray-800 mb-1">카테고리</span>
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
                {filteredCategories.map((category) => (
                  <Combobox.Option key={category} value={category}>
                    {({ active, selected }) => (
                      <li
                        className={combineClassnames(
                          "group flex p-2 space-x-0.5 cursor-pointer hover:bg-main-500 hover:text-white hover:font-bold focus:bg-main-500",
                          active ? "bg-main-500 text-white font-bold" : "bg-white text-black",
                        )}
                      >
                        {/* FIXME: 아니 왜,,,, group-focus, active, group-focus-within,,, */}
                        {selected ? (
                          <CheckIcon
                            className={combineClassnames(
                              "w-5 h-5 text-main-500 group-hover:text-white",
                              // active ? "text-white" : "",
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

export default Category;
