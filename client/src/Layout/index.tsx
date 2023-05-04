// component
import Header from "./Header";
import Nav from "./Nav";
import Main from "./Main";
import Footer from "./Footer";

/** 2023/03/23 - 레이아웃을 적용하는 컴포넌트 - by 1-blue */
const Layout: React.FC<React.PropsWithChildren> = ({ children }) => (
  <>
    <Header />
    <Nav />
    <Main>{children}</Main>
    <Footer />
  </>
);

export default Layout;
