"use client";

import { LoadingProvider } from "@/context/LoadingProvider";

// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";

import { ModalProvider, Modal } from "../components/login";

/** 2023/05/10 - 화면 구성의 Root 경로 - by Kadesti */
const Layout: React.FC<React.PropsWithChildren> = ({ children }) => (
  <LoadingProvider>
    <ModalProvider>
      <Modal />
      <Header />
      <Nav />
      <Main>{children}</Main>
      <Footer />
    </ModalProvider>
  </LoadingProvider>
);

export default Layout;
