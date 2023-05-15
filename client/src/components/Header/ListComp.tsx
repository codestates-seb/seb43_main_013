/** 2023/05/11 - 인기 검색어 리스트 - by Kadesti */
const ListComp = ({ label }: { label: string }) => {
  return (
    <div className="w-1/2 flex flex-col items-center p-6">
      <h2 className="text-4xl mb-3">{label}</h2>
      <ul className="bg-slate-300 w-full h-full rounded-2xl p-6 flex flex-col justify-between"></ul>
    </div>
  );
};

export default ListComp;
