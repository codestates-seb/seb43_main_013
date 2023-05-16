"use client";
import { useState } from "react";
import RankModal from "./RankModal";
import RankClose from "./RankClose";

/** 2023/05/11 - 실시간 검색어 순위 - by Kadesti */
const RankMenu = () => {
  const rankBind = useState(false);
  const [rankModal] = rankBind;

  return rankModal ? <RankModal rankBind={rankBind} /> : <RankClose rankBind={rankBind} />;
};

export default RankMenu;
