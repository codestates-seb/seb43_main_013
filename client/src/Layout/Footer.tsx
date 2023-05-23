/** 2023/05/24 - 푸터 컴포넌트 - by leekoby */
const Footer = () => {
  return (
    <div className="sticky w-full min-h-max bg-main-50 top-0 z-5 mt-24 pb-3">
      <div className="w-full p-5 relative  flex justify-center">
        <div className="flex flex-row w-full">
          <ul className="flex flex-row justify-center p-3 w-full space-x-5 md:space-x-36">
            <li className="text-left">
              {/* <img className="w-36 mt-[-5] h-auto" src={logo} alt="로고" /> */}
              <h3 className="font-bold text-2xl md:text-3xl">Creator Connect</h3>
              <br />
              <br />
              <h3 className="font-bold text-lg md:text-xl">TEAM</h3>
              <p className="font-bold text-lg md:text-2xl">13일의 금요일</p>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold">FRONT-END</h3>
              <ul className="space-y-1">
                <li>REACT</li>
                <li>TYPESCRIPT</li>
                <li>NEXT13(beta)</li>
                <li>TANSTACK-QUERY</li>
                <li>ZUSTAND</li>
                <li>TAILWINDCSS</li>
                <li>CHAKRAUI</li>
                <li>VERCEL</li>
              </ul>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold">BACK-END</h3>
              <ul className="space-y-1">
                <li>JAVA</li>
                <li>INTELLIJIDEA</li>
                <li>SPRING BOOT</li>
                <li>SPRING SECURITY</li>
                <li>JWT</li>
                <li>AMAZON EC2</li>
                <li>MYSQL</li>
                <li>H2</li>
              </ul>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold">FRONT-END</h3>
              <ul className="space-y-4 ">
                <li className="text-2xl md:text-3xl">김형욱</li>
                <li className="text-2xl md:text-3xl">박상은</li>
                <li className="text-2xl md:text-3xl">이호승</li>
              </ul>
            </li>
            <li className="text-left space-y-2">
              <h3 className="font-bold">BACK-END</h3>
              <ul className="space-y-4">
                <li className="text-2xl md:text-3xl">이미연</li>
                <li className="text-2xl md:text-3xl">윤영우</li>
                <li className="text-2xl md:text-3xl">임종영</li>
                <li className="text-2xl md:text-3xl">홍승현</li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Footer;
