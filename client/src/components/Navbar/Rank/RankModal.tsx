// import currentRank from "../currentRank";

/** 2023/05/11 - 실시간 검색어 모달창 - by Kadesti */
const RankModal = ({ rankBind }: { rankBind: [boolean, React.Dispatch<boolean>] }) => {
  const [rankModal, setRankModal] = rankBind;
  return (
    <div className="flex flex-col p-3 absolute right-0 top-0 cursor-pointer bg-white shadow-xl rounded-b-lg text-xl"></div>
  );
};

export default RankModal;
