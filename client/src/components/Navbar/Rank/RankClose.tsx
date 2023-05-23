import RankModalProps from "./rankModalProps";

/** 2023/05/11 - 실시간 검색어 닫힌 상태 - by Kadesti */
const RankClose: RankModalProps = ({ rankBind, curRank }) => {
  const [rankModal, setRankModal] = rankBind;

  // const interval = window.setInterval(rollingCallback, 3000);

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

    // //.prev 클래스 삭제
    // if(rollingstate === "prev") rollingstate = "del"

    // //.current -> .prev
    // if(rollingstate === "current") rollingstate = "prev"

    // //.next -> .current
    // if(rollingstate === "next") rollingstate = "current"

    // //다음 목록 요소가 널인지 체크
    // if(rollingstate === "next" && /* 다음이 null이 아닌 지 */) {}
    // else {/* 처음꺼를 null로 */}
  };

  return (
    <div className="flex justify-between cursor-pointer relative">
      <button
        className="hover:text-rose-400 flex"
        onClick={() => {
          if (rankModal) setRankModal(false);
          else setRankModal(true);
        }}
      >
        <div className="text-xl">1</div>
        <div className="ml-7 text-xl">{curRank[0]}</div>
      </button>
    </div>
  );
};

export default RankClose;
