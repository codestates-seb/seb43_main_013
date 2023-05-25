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
  { id: 3, categoryName: "전체" },
  { id: 1, categoryName: "영화" },
  { id: 2, categoryName: "자동차" },
  { id: 10, categoryName: "음악" },
  { id: 15, categoryName: "동물" },
  { id: 17, categoryName: "스포츠" },
  { id: 20, categoryName: "게임" },
  { id: 22, categoryName: "인물/블로그" },
  { id: 23, categoryName: "코미디" },
  { id: 24, categoryName: "엔터테인먼트" },
  { id: 25, categoryName: "뉴스" },
  { id: 26, categoryName: "노하우" },
  { id: 28, categoryName: "과학기술" },
];
const YoutubeSlide = () => {
  // 카테고리키
  const [youtubeCategoriesKey, setYoutubeCategoriesKey] = useState(3);
  // 카테고리 키 변경 함수
  const handleCategoryClick = (id: number) => {
    setYoutubeCategoriesKey(id);
  };

  // 유튜브 데이터 패칭
  const { data } = useFetchYoutubeList({
    youtubeCategoryId: youtubeCategoriesKey,
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

  useEffect(() => {
    const handleResize = () => {
      setWidth(window.innerWidth);

      if (width <= 768) {
        setSliderPage(1);
      } else if (width <= 1200) {
        setSliderPage(2);
      } else {
        setSliderPage(3);
      }
    };
    // 새로 고침 시 페이지 값을 설정
    handleResize();
    // 리사이즈 이벤트 핸들러 등록
    window.addEventListener("resize", handleResize);
    return () => {
      // 컴포넌트가 언마운트될 때 리스너 해제
      window.removeEventListener("resize", handleResize);
    };
  }, [width]);

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
    speed: 3000,
    arrows: false,
    draggable: true,
    autoplay: true,
    dots: false,
    swipeToSlide: true,
    afterChange: handleAfterChange,
  };

  return (
    <>
      <div className="flex justify-between my-4">
        <div className="space-y-4">
          <h1 className="font-bold text-xl">인기 급상승📈동영상</h1>
          <div className="flex flex-wrap gap-x-2 gap-y-2">
            {youtubeCategories.map((category) => (
              <li key={category.id} className="list-none">
                <button
                  type="button"
                  onClick={() => handleCategoryClick(category.id)}
                  className={`w-full px-5 text-sm leading-8 duration-200 rounded hover:bg-main-400 active:bg-main-500 hover:text-white hover:scale-105 transtition hover:shadow-md hover:shadow-sub-500/50
                ${
                  youtubeCategoriesKey === category.id
                    ? "bg-main-500 text-white shadow-lg shadow-sub-500/50"
                    : "bg-sub-100 "
                }`}
                >
                  {category.categoryName}
                </button>
              </li>
            ))}
          </div>
        </div>
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
              className="fixed top-0 left-0 w-screen h-screen bg-black bg-opacity-80 flex justify-center items-center z-40"
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
