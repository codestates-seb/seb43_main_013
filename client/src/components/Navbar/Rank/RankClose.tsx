import RankModalProps from "./rankModalProps";

/** 2023/05/11 - 실시간 검색어 닫힌 상태 - by Kadesti */
const RankClose: RankModalProps = ({ rankBind, curRank }) => {
  const [rankModal, setRankModal] = rankBind;

  let rollingstate = "";
  const rollingCallback = () => {
    switch (rollingstate) {
      case "del":
        return "";
      case "prev":
        return "";
      case "current":
        return "";
      case "next":
        return "";
      default:
    }
  };

  return (
    <button
      className="flex justify-between cursor-pointer relative hover:text-main-400 w-40 pr-3"
      onClick={() => {
        if (rankModal) setRankModal(false);
        else setRankModal(true);
      }}
    >
      <div className="text-xl">1</div>
      <div className="text-xl">{curRank[0]}</div>
    </button>
  );
};

export default RankClose;
