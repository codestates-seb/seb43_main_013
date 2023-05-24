import { usePathname } from "next/navigation";

const headerArr = () => {
  const pathname = usePathname();

  const margin = pathname === "/" ? "mr-14" : "mr-5";
  const activeLeft = `${margin} text-main-400 border-b-2 border-b-main-400 h-8`;
  const activeRight = "text-main-400 border-b-2 border-b-main-400 h-8";

  const homePath = pathname === "/" ? activeLeft : "mr-14";
  const noticePath = pathname === "/notice" ? activeRight : "";

  const loginPath = pathname === "/login" ? activeLeft : "mr-5";
  const signUpPath = pathname === "/signup" ? activeRight : "";

  const leftArr = [
    {
      path: "/",
      name: "커뮤니티",
      style: homePath,
    },
    {
      path: "/notice",
      name: "공지사항",
      style: noticePath,
    },
  ];

  const rightArr = [
    {
      path: "/login",
      name: "로그인",
      style: loginPath,
    },
    {
      path: "/signup",
      name: "회원가입",
      style: signUpPath,
    },
  ];

  return { leftArr, rightArr };
};

export default headerArr;
