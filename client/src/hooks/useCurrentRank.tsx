"use client";

import axios from "axios";
const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

type rank = (setCurRank: React.Dispatch<string[]>) => void;
const useCurrentRank: rank = (setCurRank) => {
  const currentRank = async () => {
    const rankArr = await axios.get(`${baseUrl}/api/keyword/daily`);
    return rankArr.data.data;
  };
  currentRank().then((res) => setCurRank(res));
};

export default useCurrentRank;
