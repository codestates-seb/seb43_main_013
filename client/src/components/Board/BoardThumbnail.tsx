import { getYoutubeThumbnail } from "@/libs";
import Image from "next/image";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  position: "main" | "board";
  url: string;
  alt?: string;
  className?: string;
}

/** 2023/05/12 - 게시판의 썸네일 - by 1-blue */
const BoardThumbnail: React.FC<Props> = ({ url, alt, className, position }) => {
  let src = "";
  // 1. 유튜브 링크가 아니면
  if (!url.includes("https://www.youtube.com")) src = url;
  // 2. 유튜브 링크라면
  else src = getYoutubeThumbnail(url);

  return (
    <figure
      className={twMerge(
        `relative w-full  max-h-[800px] m-0 ${
          position === "main" ? "h-[50vw] md:h-[55vw] lg:h-[360px]" : "h-[50vw] lg:h-[290px]"
        }`,
        className,
      )}
    >
      <Image
        src={src}
        alt={alt || "썸네일 이미지"}
        layout="fill"
        objectFit="contain"
        placeholder="blur"
        blurDataURL={`${url}?auto=format,compress&q=1&blur=500&w=2`}
      />
    </figure>
  );
};

export default BoardThumbnail;
