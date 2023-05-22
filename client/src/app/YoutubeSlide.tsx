"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import { useEffect, useState } from "react";
import SlideWrapper from "../components/BoardMain/Slide/SlideWrapper";

import NotSearch from "@/components/Svg/NotSearch";
import { useFetchYoutubeList } from "@/hooks/query/useFetchYoutubeList";
import YoutubeItem from "./YoutubeItem";
import YouTube from "react-youtube";

/** 2023/05/22- 메인화면 인기 급상승 슬라이드 컴포넌트 - by leekoby */

const youtubeCategories = [
  { 3: "전체" },
  { 1: "영화" },
  { 2: "Autos & Vehicles" },
  { 10: "음악" },
  { 15: "동물" },
  { 17: "스포츠" },
  { 20: "게임" },
  { 22: "People" },
  { 23: "Comedy" },
  { 24: "Entertainment" },
  { 25: "News" },
  { 26: "Howto&Style" },
  { 28: "Science&Technology" },
];
const YoutubeSlide = () => {
  // 유튜브 데이터 패칭
  const { data } = useFetchYoutubeList({
    youtubeCategoryId: 3,
  });

  const [isOpen, setIsOpen] = useState(false);
  const [activeVideoId, setActiveVideoId] = useState<string>("");

  const handleOpen = (youtubeId: string) => {
    setIsOpen(true);
    setActiveVideoId(youtubeId);
  };
  const handleClose = () => {
    setIsOpen(false);
    setActiveVideoId("");
  };

  //포커스된 슬라이드 인덱스
  const [currentSlideIndex, setCurrentSlideIndex] = useState<number>(0);
  //슬라이더 페이지 갯수
  const [silderPage, setSliderPage] = useState<number>(3);
  //현재 창의 width 길이
  const [width, setWidth] = useState<number>(typeof window !== "undefined" ? window.innerWidth : 0);

  if (typeof window !== "undefined") {
    window.addEventListener("resize", () => {
      setWidth(window.innerWidth);
      // 변화된 width 값을 이용하여 필요한 작업 수행
      if (width <= 768) {
        setSliderPage(1);
      } else if (width <= 1200) {
        setSliderPage(2);
      } else {
        setSliderPage(3);
      }
    });
  }
  /**2023-05-22 - 새로고침시 width에 따라 페이징 변환 - leekoby */
  useEffect(() => {
    if (width <= 768) {
      setSliderPage(1);
    } else {
      setSliderPage(3);
    }
  }, []);

  /**2023-05-22 - 슬라이드 인덱스 - leekoby */
  const handleAfterChange = (index: number) => {
    setCurrentSlideIndex(index);
  };

  /**2023-05-22 - 슬라이드 설정 옵션 - leekoby */
  const settings = {
    adaptiveHeight: true,
    className: "center",
    centerMode: true,
    centerPadding: "60px",
    infinite: true,
    slidesToShow: silderPage,
    slidesToScroll: 1,
    speed: 500,
    arrows: false,
    draggable: true,
    // autoplay: true,
    dots: false,
    swipeToSlide: true,
    afterChange: handleAfterChange,
  };

  return (
    <>
      <div className="flex justify-between">
        <h1 className="font-bold text-xl">인기 급상승📈동영상</h1>
      </div>

      {data?.data.length === 0 ? (
        <NotSearch />
      ) : (
        <div className="">
          <Slider {...settings}>
            {data?.data.map((item) => {
              return (
                <SlideWrapper>
                  <YoutubeItem key={item.videoId} youtubeData={item} onOpen={handleOpen} />
                </SlideWrapper>
              );
            })}
          </Slider>
          {isOpen && (
            <div
              className="fixed top-0 left-0 w-screen h-screen bg-black bg-opacity-70 flex justify-center items-center z-40"
              onClick={handleClose}
            >
              <div className="w-[500px] h-[300px] md:h-[450px] md:w-[750px] lg:h-[600px] lg:w-[1100px] ">
                <YouTube
                  key={activeVideoId}
                  videoId={activeVideoId}
                  opts={{
                    width: "100%",
                    height: "100%",
                    playerVars: {
                      autoplay: 1, // 자동재생
                      rel: 0, // 관련 동영상 표시하지 않음 (근데 별로 쓸모 없는 듯..)
                      modestbranding: 1, // 컨트롤 바에 youtube 로고를 표시하지 않음
                    },
                  }}
                  className="h-full w-full"
                />
              </div>
            </div>
          )}
        </div>
      )}
    </>
  );
};
export default YoutubeSlide;
