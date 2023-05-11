import Link from "next/link";

const link = [
  {
    name: "자유",
    link: "/free",
  },
  {
    name: "피드백",
    link: "/feedback",
  },
  {
    name: "홍보",
    link: "/promotion",
  },
  {
    name: "구인",
    link: "/job",
  },
];

/** 2023/05/11 - 내이베이션바 라우팅 컴포넌트 - by Kadesti */
const NavRoute = () => {
  return (
    // <ul className="max-w-[300px] w-80 flex justify-between text-xl ">
    <ul className="w-80 flex justify-between text-xl ">
      {link.map((item) => {
        const { name, link } = item;

        return (
          <Link href={link} key={link}>
            <li className="cursor-pointer text-black hover:text-rose-400">{name}</li>
          </Link>
        );
      })}
    </ul>
  );
};

export default NavRoute;
