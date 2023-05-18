"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useFetchFeedbackBoardList } from "@/hooks/query/useFetchFeedbackBoardList";
import Link from "next/link";
import ContentItem from "@/app/feedback/FeedbackContentItem";
import FullSpinner from "@/components/Spinner/FullSpinner";
import { ReactNode, useEffect, useState } from "react";
import SlideWrapper from "./SlideWrapper";

const FeedbackPostsSlide = () => {
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
      if (width <= 900) {
        setSliderPage(1);
        // } else if (width <= 1200) {
        //   setSliderPage(2);
      } else {
        setSliderPage(2);
      }
    });
  }
  /**2023-05-17 - 새로고침시 width에 따라 페이징 변환 - leekoby */
  useEffect(() => {
    if (width <= 900) {
      setSliderPage(1);
    } else {
      setSliderPage(2);
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
    afterChange: handleAfterChange,
    infinite: true,
    draggable: true,
    swipeToSlide: true,
    slidesToShow: silderPage,
    autoplay: true,
    speed: 3000,
    dots: false,
    arrow: true,
    appendDots: appendDots,
    customPaging: customPaging,
    pauseOnHover: true,
  };

  /** 2023/05/17 피드백 목록 get 요청 - by leekoby */
  const { data } = useFetchFeedbackBoardList({
    selected: "",
    selectedFeedback: 1,
    sorted: "인기순",
    page: 1,
    size: 10,
  });

  if (!data) return <FullSpinner />;

  return (
    <div>
      <div className="flex justify-between">
        <h1 className="font-bold text-xl">지금 HOT🔥한 피드백</h1>
        <Link href="/feedback">
          <button className="text-3xl focus:outline-none">+</button>
        </Link>
      </div>
      <div>
        <Slider {...settings}>
          {data.pages.map((page) =>
            page.data.map((innerData) => {
              return (
                <SlideWrapper className="h-full hover:cursor-pointer" key={innerData.feedbackBoardId}>
                  <Link href={`/feedback/${innerData.feedbackBoardId}`}>
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

export default FeedbackPostsSlide;
