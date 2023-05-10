"use client";

import { LoadingProvider } from "@/context/LoadingProvider";

// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";

/** 2023/03/23 - 레이아웃을 적용하는 컴포넌트 - by 1-blue */
const Layout: React.FC<React.PropsWithChildren> = ({ children }) => (
  <>
    <LoadingProvider>
      <Header />
      <Nav />
      <Main>{children}</Main>
      <Footer />
    </LoadingProvider>
  </>
);

export default Layout;
