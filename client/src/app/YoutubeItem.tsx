"use client";

import { MouseEventHandler, useState } from "react";
import { YoutubeList } from "@/types/api";
import YouTube from "react-youtube";
import Image from "next/image";

interface Props {
  youtubeData: YoutubeList;
  onOpen: (youtubeId: string) => void;
}

/** 2023/05/22- 인기 동영상 슬라이드 아이템 - by leekoby */
const YoutubeItem: React.FC<Props> = ({ youtubeData, onOpen }) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleClick: MouseEventHandler<HTMLImageElement> = () => {
    onOpen(youtubeData.youtubeId);
  };

  return (
    <>
      <div className="relative inline-block w-auto h-[225px] overflow-hidden rounded-lg bg-main-100 shadow-main m-1 p-1">
        <div className="inline-block w-full h-full">
          <img
            src={`${youtubeData.thumbnailUrl}`}
            width={400}
            height={500}
            alt={`${youtubeData.title}`}
            placeholder="blur"
            // blurDataURL="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mM0/g8AAWsBNAUUB5MAAAAASUVORK5CYII="
          />
        </div>
        <div
          className="absolute inset-0 bg-black bg-opacity-70 opacity-0 hover:opacity-100 flex justify-center items-center transition-opacity duration-300 hover:cursor-pointer w-full h-full hover:shadow-black/30 hover:shadow-lg"
          onClick={handleClick}
        >
          <span className="text-white text-2xl w-4/5 text-center align-middle break-keep truncate-3">
            {youtubeData.title}
          </span>
        </div>
      </div>
    </>
  );
};
export default YoutubeItem;
