// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";

import { ModalProvider, Modal } from "../components/login";

/** 2023/05/04 - 레이아웃을 적용하는 컴포넌트 - by 1-blue */
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
