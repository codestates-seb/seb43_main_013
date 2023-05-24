import Logo4 from "@/public/logo/main-logo4.png";
import Image from "next/image";

/** 2023/05/24 - 푸터 컴포넌트 - by leekoby */
const FooterContent = () => {
  return (
    <div className="sticky w-full min-h-max bg-gray-700 text-white top-0 z-5 mt-24 pb-3">
      <div className="w-full py-5 relative  flex justify-center">
        <div className="flex flex-row w-full mt-7">
          <ul className="flex flex-row justify-around w-full space-x-4 lg:4">
            <li className="text-left">
              {/* <img className="w-36 mt-[-5] h-auto" src={logo} alt="로고" /> */}
              <div className="flex flex-col lg:flex-row items-center justify-center lg:gap-x-2">
                <Image src={Logo4} alt="" className="inline-block lg:h-[72px] lg:w-[72px]" />
                <h3 className="font-bold text-2xl lg:text-3xl">
                  Creator <br /> Connect
                </h3>
              </div>
              <br />
              <br />
              <div className="space-y-2">
                <h3 className="font-bold text-md lg:text-xl">TEAM</h3>
                <p className="font-bold text-md lg:text-2xl">13일의 금요일</p>
              </div>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold text-sm lg:text-lg ">FRONT-END</h3>
              <ul className="space-y-1">
                <li className="text-sm lg:text-lg">REACT</li>
                <li className="text-sm lg:text-lg">TYPESCRIPT</li>
                <li className="text-sm lg:text-lg">NEXT13(beta)</li>
                <li className="text-sm lg:text-lg">TANSTACK-QUERY</li>
                <li className="text-sm lg:text-lg">ZUSTAND</li>
                <li className="text-sm lg:text-lg">TAILWINDCSS</li>
                <li className="text-sm lg:text-lg">CHAKRAUI</li>
                <li className="text-sm lg:text-lg">VERCEL</li>
              </ul>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold text-sm lg:text-lg ">BACK-END</h3>
              <ul className="space-y-1">
                <li className="text-sm lg:text-lg">JAVA</li>
                <li className="text-sm lg:text-lg">SPRING BOOT</li>
                <li className="text-sm lg:text-lg">SPRING SECURITY</li>
                <li className="text-sm lg:text-lg">SPRINGDATA</li>
                <li className="text-sm lg:text-lg">JWT</li>
                <li className="text-sm lg:text-lg">AMAZON EC2</li>
                <li className="text-sm lg:text-lg">MYSQL</li>
                <li className="text-sm lg:text-lg">REDIS</li>
                <li className="text-sm lg:text-lg">RDS</li>
                <li className="text-sm lg:text-lg">JPA</li>
              </ul>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold text-sm lg:text-lg ">FE MEMBER</h3>
              <ul className="space-y-4 ">
                <li className="text-lg lg:text-2xl">김형욱</li>
                <li className="text-lg lg:text-2xl">박상은</li>
                <li className="text-lg lg:text-2xl">이호승</li>
              </ul>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold text-sm md:text-lg break-keep">BE MEMBER</h3>
              <ul className="space-y-4">
                <li className="text-lg md:text-2xl">이미연</li>
                <li className="text-lg md:text-2xl">윤영우</li>
                <li className="text-lg md:text-2xl">임종영</li>
                <li className="text-lg md:text-2xl">홍승현</li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default FooterContent;
