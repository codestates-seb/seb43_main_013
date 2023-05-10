import Link from "next/link";

/** 2023/05/10 - 커뮤니티 트렌드 라우팅 텍스트 - by Kadesti */
const CommuTrend = () => {
  return (
    <div className="flex flex-row mr-10">
      <Link href="/">
        <h3 className="text-2xl mr-5 break-keep cursor-pointer text-black hover:text-rose-400">커뮤니티</h3>
      </Link>
      <h3 className="text-2xl break-keep cursor-pointer text-black hover:text-rose-400">트렌드</h3>
    </div>
  );
};

export default CommuTrend;
