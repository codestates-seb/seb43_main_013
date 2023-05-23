import React from "react";
import WordCloud from "react-d3-cloud";

import { useRouter } from "next/navigation";
import { useFetchDailyKeywords, useFetchMonthlyKeywords, useFetchWeeklyKeywords } from "@/hooks/query";

interface Props {
  type: "daily" | "weekly" | "monthly";
}

const KeywordCloud: React.FC<Props> = ({ type }) => {
  const router = useRouter();

  const { dailyKeywords } = useFetchDailyKeywords();
  const { weeklyKeywords } = useFetchWeeklyKeywords();
  const { MonthlyKeywords } = useFetchMonthlyKeywords();

  if (!dailyKeywords || !weeklyKeywords || !MonthlyKeywords) return <></>;

  let keywords: { text: string; value: number }[] = [];

  if (type === "daily")
    keywords = dailyKeywords.data
      .reverse()
      .map((keyword, i) => ({ text: keyword, value: (i + 10) * 10 * (Math.random() * 10) }));
  if (type === "weekly")
    keywords = weeklyKeywords.data
      .reverse()
      .map((keyword, i) => ({ text: keyword, value: (i + 10) * 10 * (Math.random() * 10) }));
  if (type === "monthly")
    keywords = MonthlyKeywords.data
      .reverse()
      .map((keyword, i) => ({ text: keyword, value: (i + 10) * 10 * (Math.random() * 10) }));

  return (
    <WordCloud
      data={keywords}
      font="BMJUA"
      fontWeight="bold"
      fontSize={(word) => Math.log2(word.value) * 5}
      spiral="archimedean"
      rotate={(word) => word.value % 360}
      padding={4}
      random={Math.random}
      onWordClick={(e, d) => {
        router.push(`/search?keyword=${d.text}`);
      }}
      onWordMouseOver={(e, d) => {
        // console.log(`onWordMouseOver: ${d.text}`);
      }}
      onWordMouseOut={(e, d) => {
        // console.log(`onWordMouseOut: ${d.text}`);
      }}
    />
  );
};

export default React.memo(KeywordCloud);
