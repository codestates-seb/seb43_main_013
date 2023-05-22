import { Fragment } from "react";
import Link from "next/link";
import { Menu, Transition } from "@headlessui/react";
import {
  ChevronDownIcon,
  UserCircleIcon as OUserCircleIcon,
  PencilIcon as OPencilIcon,
  ArrowLeftOnRectangleIcon as OArrowLeftOnRectangleIcon,
} from "@heroicons/react/24/outline";
import { apiLogOut } from "@/apis";
import { useLoadingStore } from "@/store";
import useCustomToast from "@/hooks/useCustomToast";
import { useMemberStore } from "@/store/useMemberStore";

// type
interface Props {
  nickname: string;
  memberId: number;
}

/** 2023/05/20 - dropdown - by 1-blue */
const InfoDropdown: React.FC<Props> = ({ nickname, memberId }) => {
  const toast = useCustomToast();
  const { loading } = useLoadingStore();
  const { setMember } = useMemberStore();

  const onClickLogOut = async () => {
    try {
      loading.start();

      await apiLogOut({});

      localStorage.clear();

      toast({ title: "로그아웃 했습니다.", status: "info" });
      setMember(null);
    } catch (error) {
      console.error(error);

      toast({ title: "로그아웃에 실패했습니다.", status: "error" });
    } finally {
      loading.end();
    }
  };

  return (
    <Menu>
      <Menu.Button className="group flex items-center space-x-1">
        <span className="transition-colors group-hover:text-main-400">{nickname}</span>
        <ChevronDownIcon className="w-5 h-5 stroke-2 transition-colors group-hover:stroke-main-400" />
      </Menu.Button>
      <Transition
        enter="transition duration-100 ease-out"
        enterFrom="transform scale-95 opacity-0"
        enterTo="transform scale-100 opacity-100"
        leave="transition duration-75 ease-out"
        leaveFrom="transform scale-100 opacity-100"
        leaveTo="transform scale-95 opacity-0"
        className="absolute top-14 -left-4 z-10 p-1 bg-sub-50 shadow-md rounded-md"
      >
        <Menu.Items>
          <Menu.Item as={Fragment}>
            {({ active }) => (
              <Link
                href={`/profile/${memberId}`}
                className={`${
                  active ? "bg-main-500 text-white" : "text-gray-900"
                } group flex w-full items-center rounded-md px-2 py-2 text-sm transition-colors`}
              >
                <OUserCircleIcon
                  className="transition-colors mr-2 h-7 w-7 stroke-gray-600 stroke-2 group-hover:stroke-white"
                  aria-hidden="true"
                />
                내 정보
              </Link>
            )}
          </Menu.Item>
          <Menu.Item as={Fragment}>
            {({ active }) => (
              <Link
                href="/feedback/write"
                className={`${
                  active ? "bg-main-500 text-white" : "text-gray-900"
                } group flex w-full items-center rounded-md px-2 py-2 text-sm transition-colors`}
              >
                <OPencilIcon
                  className="transition-colors mr-2 h-7 w-7 stroke-gray-600 stroke-2 group-hover:stroke-white"
                  aria-hidden="true"
                />
                글쓰기
              </Link>
            )}
          </Menu.Item>
          <Menu.Item as={Fragment}>
            {({ active }) => (
              <button
                type="button"
                onClick={onClickLogOut}
                className={`${
                  active ? "bg-main-500 text-white" : "text-gray-900"
                } group flex w-full items-center rounded-md px-2 py-2 text-sm transition-colors`}
              >
                <OArrowLeftOnRectangleIcon
                  className="transition-colors mr-2 h-7 w-7 stroke-gray-600 stroke-2 group-hover:stroke-white"
                  aria-hidden="true"
                />
                로그아웃
              </button>
            )}
          </Menu.Item>
        </Menu.Items>
      </Transition>
    </Menu>
  );
};

export default InfoDropdown;
