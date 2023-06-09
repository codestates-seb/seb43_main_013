"use client";

// store
import { useLoadingStore } from "@/store";

// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";
import FullSpinner from "@/components/Spinner/FullSpinner";

/** 2023/05/10 - 화면 구성의 Root 경로 - by Kadesti */
const Layout: React.FC<React.PropsWithChildren> = ({ children }) => {
  const { isLoading } = useLoadingStore((state) => state);

  return (
    <>
      <div id="root">
        <Header />
        <Nav />
        <Main>{children}</Main>
        <Footer />
      </div>
      {isLoading && <FullSpinner />}
    </>
  );
};

export default Layout;
