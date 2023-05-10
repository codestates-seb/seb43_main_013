import Link from "next/link";
import { ChevronUpIcon, ChevronDownIcon, UserCircleIcon } from "@/components/HeaderIcon";
import NickModal from "./NickModal";

/**
 * 2023/05/10 - 로그인 상태의 검색창 우측 메뉴 - by Kadesti
 * @param nickModal 모달 창 상태
 * @param setNickModal 세터함수
 */
const IsLoginSide = ({ nickState }: { nickState: [boolean, React.Dispatch<boolean>] }) => {
  const [nickModal, setNickModal] = nickState;

  return (
    <div className="flex flex-row pr-5 items-center">
      <div
        onClick={() => setNickModal(!nickModal)}
        className="break-keep flex items-center mr-5 cursor-pointer hover:text-slate-400"
      >
        <div className="flex relative">
          <span className="mr-2 text-2xl cursor-pointer">닉네임</span>
          {nickModal ? (
            <>
              <NickModal setNickModal={setNickModal} />
              <ChevronUpIcon className="w-7 cursor-pointer" />
            </>
          ) : (
            <ChevronDownIcon className="w-7 cursor-pointer" />
          )}
        </div>
      </div>
      <UserCircleIcon className="w-12 cursor-pointer hover:text-slate-400" />
    </div>
  );
};

/** 2023/05/10 - 비로그인 상태의 검색창 우측 메뉴 - by Kadesti */
const NoneLoginSide = () => {
  return (
    <div className="flex items-center">
      <h3
        // onClick={modalActions.openModal}
        className="text-2xl mr-5 break-keep cursor-pointer text-black hover:text-rose-400"
      >
        로그인
      </h3>
      <Link href="/signup">
        <h3 className="text-2xl break-keep cursor-pointer text-black hover:text-rose-400">회원가입</h3>
      </Link>
    </div>
  );
};

export { IsLoginSide, NoneLoginSide };
