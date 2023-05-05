/** 2023/05/04 - 네브 컴포넌트 - by Kadesti */
const Nav = () => {
  return (
    <nav className="bg-white h-[64px] shadow-xl flex justify-center">
      <div className="flex w-full max-w-5xl justify-between items-center">
        <div className="flex text-xl justify-between w-80">
          <div className="cursor-pointer text-black hover:text-rose-400">자유</div>
          <div className="cursor-pointer text-black hover:text-rose-400">피드백</div>
          <div className="cursor-pointer text-black hover:text-rose-400">홍보</div>
          <div className="cursor-pointer text-black hover:text-rose-400">구인</div>
        </div>
        <div className="flex w-20 justify-between mr-12 cursor-default">
          <div>1</div>
          <div className="text-black hover:text-rose-400 cursor-pointer">해방일지</div>
        </div>
      </div>
    </nav>
  );
};

export default Nav;
