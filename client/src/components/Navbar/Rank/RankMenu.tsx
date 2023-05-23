"use client";
import { useEffect, useState } from "react";
import RankModal from "./RankModal";
import RankClose from "./RankClose";
import useCurrentRank from "@/hooks/useCurrentRank";

/** 2023/05/11 - 실시간 검색어 순위 - by Kadesti */
const RankMenu = () => {
  const rankBind = useState(false);
  const [rankModal] = rankBind;
  const [curRank, setCurRank] = useState([""]);

  useEffect(() => {
    useCurrentRank(setCurRank);
  }, []);

  // console.log("curRank: ", curRank);

  return (
    <>
      <RankClose rankBind={rankBind} curRank={curRank} />
      {rankModal && <RankModal rankBind={rankBind} curRank={curRank} />}
    </>
  );
};

export default RankMenu;
