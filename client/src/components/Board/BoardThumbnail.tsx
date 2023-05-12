import { getYoutubeThumbnail } from "@/libs";
import Image from "next/image";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  url: string;
  alt?: string;
  className?: string;
}

/** 2023/05/12 - 게시판의 썸네일 - by 1-blue */
const BoardThumbnail: React.FC<Props> = ({ url, alt, className }) => {
  let src = "";
  // 1. 유튜브 링크가 아니면
  if (!url.includes("https://www.youtube.com")) src = url;
  // 2. 유튜브 링크라면
  else src = getYoutubeThumbnail(url);

  return (
    <figure className={twMerge("relative w-full h-[55vw] max-h-[800px] object-fill", className)}>
      <Image src={src} alt={alt || "썸네일 이미지"} fill className="" />
    </figure>
  );
};

export default BoardThumbnail;
