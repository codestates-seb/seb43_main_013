// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";

import { ModalProvider, Modal } from "../components/login";

/** 2023/05/10 - 화면 구성의 Root 경로 - by Kadesti */
const Layout: React.FC<React.PropsWithChildren> = ({ children }) => (
  <>
    <ModalProvider>
      <Modal />
      <div id="root">
        <Header />
        <Nav />
        <Main>{children}</Main>
        <Footer />
      </div>
    </ModalProvider>
  </>
);

export default Layout;
