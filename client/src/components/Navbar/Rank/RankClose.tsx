import currentRank from "../currentRank";

/** 2023/05/11 - 실시간 검색어 닫힌 상태 - by Kadesti */
const RankClose = ({ rankBind }: { rankBind: [boolean, React.Dispatch<boolean>] }) => {
  const [, setRankModal] = rankBind;
  const item = currentRank[0];

  return (
    <div className="flex justify-between mr-6 text-xl cursor-pointer relative">
      <div className="hover:text-rose-400 flex">
        <div>{item.rank}</div>
        <div
          onClick={() => {
            setRankModal(true);
          }}
          className="ml-7"
        >
          {item.content}
        </div>
      </div>
    </div>
  );
};

export default RankClose;
