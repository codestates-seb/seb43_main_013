/** 2023/05/11 - 인기 검색어 리스트 - by Kadesti */
const ListComp = ({ label, data }: { label: string; data: string[] }) => {
  return (
    <div className="w-1/2 h-full flex flex-col items-start mr-10">
      <h2 className="text-3xl mb-3">{label}</h2>
      <ul className="w-full overflow-scroll">
        {data.map((item) => {
          return <li className="bg-main-200 h-14 rounded-2xl">{item}</li>;
        })}
      </ul>
    </div>
  );
};

export default ListComp;
