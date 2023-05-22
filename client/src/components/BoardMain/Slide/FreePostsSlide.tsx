"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Link from "next/link";
import FullSpinner from "@/components/Spinner/FullSpinner";
import { ReactNode, useEffect, useState } from "react";
import SlideWrapper from "./SlideWrapper";
import { useFetchFreeBoardList } from "@/hooks/query/useFetchFreeBoardList";
import ContentItem from "@/app/free/ContentItem";

const FreePostsSlide = () => {
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
  /**2023-05-17 - 새로고침시 width에 따라 페이징 변환 - leekoby */
  useEffect(() => {
    if (width <= 768) {
      setSliderPage(1);
    } else {
      setSliderPage(3);
    }
  }, []);

  /**2023-05-17 - 슬라이드 인덱스 - leekoby */
  const handleAfterChange = (index: number) => {
    setCurrentSlideIndex(index);
  };

  /**2023-05-17 - 슬라이드 버튼 append  - leekoby */
  const appendDots = (dots: ReactNode) => {
    return (
      <div style={{ height: "0px" }}>
        <ul style={{ margin: "4px" }}> {dots} </ul>
      </div>
    );
  };
  /**2023-05-17 - 슬라이드 버튼 custom - leekoby */

  const customPaging = (i: number) => {
    return (
      <div
        className={`rounded-full w-6 h-6 flex items-center justify-center ml-1 mr-1 ${
          i === currentSlideIndex ? "bg-main-400 text-white " : "bg-sub-200 text-black "
        }`}
      >
        {i + 1}
      </div>
    );
  };

  /**2023-05-17 - 슬라이드 설정 옵션 - leekoby */
  const settings = {
    touchThreshold: 100,
    afterChange: handleAfterChange,
    className: "center",
    centerMode: true,
    // centerPadding: "60px",
    infinite: true,
    draggable: true,
    swipeToSlide: true,
    slidesToShow: silderPage,
    autoplay: true,
    // autoplaySpeed: 4000,
    speed: 500,
    dots: false,
    arrow: true,
    appendDots: appendDots,
    customPaging: customPaging,
  };

  /** 2023/05/17 자유게시판 목록 get 요청 - by leekoby */
  const { data } = useFetchFreeBoardList({
    selected: "",
    sorted: "인기순",
    page: 1,
    size: 10,
  });

  if (!data) return <FullSpinner />;

  return (
    <div>
      <div className="flex justify-between">
        <h1 className="font-bold text-xl">지금 HOT🔥한 게시글</h1>
        <Link href="/free">
          <button className="text-3xl focus:outline-none">+</button>
        </Link>
      </div>
      <div className="">
        <Slider {...settings}>
          {data.pages.map((page) =>
            page.data.map((innerData) => {
              return (
                <SlideWrapper className="hover:cursor-pointer" key={innerData.freeBoardId}>
                  <Link href={`/free/${innerData.freeBoardId}`}>
                    <div className="h-full mx-3">
                      <ContentItem props={innerData} />
                    </div>
                  </Link>
                </SlideWrapper>
              );
            }),
          )}
        </Slider>
      </div>
    </div>
  );
};

export default FreePostsSlide;
