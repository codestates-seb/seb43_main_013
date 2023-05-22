"use client";

import { useEffect } from "react";

// store
import { useLoadingStore } from "@/store";
import { useMemberStore } from "@/store/useMemberStore";

// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";
import FullSpinner from "@/components/Spinner/FullSpinner";
import useLogInStore from "@/store/useLogInStore";
import MyLogInModal from "@/components/Login/MyLogInModal";
import useSignUpStore from "@/store/useSignUpStore";
import MySignUpModal from "@/components/signup/MySignUpModal";

/** 2023/05/10 - 화면 구성의 Root 경로 - by Kadesti */
const Layout: React.FC<React.PropsWithChildren> = ({ children }) => {
  const { isLoading } = useLoadingStore((state) => state);
  const { setMember } = useMemberStore();
  const { isShowLogInModal } = useLogInStore();
  const { isShowSignUpModal } = useSignUpStore();

  /** 2023/05/16 - 로그인한 유저 데이터 전역 상태에 저장 - by 1-blue */
  useEffect(() => {
    const member = localStorage.getItem("member");
    if (!member) return;

    const parsedMember = JSON.parse(member);
    setMember(parsedMember);
  }, [setMember]);

  return (
    <>
      <Header />
      <Nav />
      <Main>{children}</Main>
      <Footer />

      {isShowLogInModal && <MyLogInModal />}
      {isShowSignUpModal && <MySignUpModal />}
      {isLoading && <FullSpinner />}
    </>
  );
};

export default Layout;
