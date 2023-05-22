"use client";

import axios from "axios";
import { useState } from "react";
const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;

type rank = () => string[];
// const currentRank: rank = () => {
const currentRank = () => {
  //   const [curRank, setCurRank] = useState([""]);
  //   const currentRank = async () => {
  //     const rankArr = await axios.get(`${baseUrl}/api/search/popularkeywords`);
  //     return rankArr.data.data;
  //   };
  //   currentRank().then((res) => setCurRank(res));
  //   return curRank;
};

export default currentRank;
