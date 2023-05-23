import { getYoutubeIframe } from "@/libs";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  url: string;
  className?: string;
}

/** 2023/05/23 - 게시판의 비디오 - by 1-blue */
const BoardIframe: React.FC<Props> = ({ url, className }) => {
  let src = "";
  // 1. 유튜브 링크가 아니면
  if (!url.includes("https://www.youtube.com")) src = url;
  // 2. 유튜브 링크라면
  else src = getYoutubeIframe(url);

  return <iframe className={twMerge("relative w-full h-[55vw] max-h-[800px] object-fill", className)} src={src} />;
};

export default BoardIframe;
