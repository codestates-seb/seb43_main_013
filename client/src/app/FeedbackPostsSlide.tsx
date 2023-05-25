"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useFetchFeedbackBoardList } from "@/hooks/query/useFetchFeedbackBoardList";
import Link from "next/link";
import FeedbackContentItem from "@/app/feedback/FeedbackContentItem";

import { ReactNode, useEffect, useState } from "react";
import SlideWrapper from "../components/BoardMain/Slide/SlideWrapper";
import NotSearch from "@/components/Svg/NotSearch";

const FeedbackPostsSlide = () => {
  //포커스된 슬라이드 인덱스
  const [currentSlideIndex, setCurrentSlideIndex] = useState<number>(0);
  //슬라이더 페이지 갯수
  const [silderPage, setSliderPage] = useState<number>(3);
  //현재 창의 width 길이
  const [width, setWidth] = useState<number>(typeof window !== "undefined" ? window.innerWidth : 0);

  useEffect(() => {
    const handleResize = () => {
      setWidth(window.innerWidth);

      if (width <= 900) {
        setSliderPage(1);
        // } else if (width <= 1200) {
        //   setSliderPage(2);
      } else {
        setSliderPage(2);
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

  /**2023-05-17 - 슬라이드 인덱스 - leekoby */
  const handleAfterChange = (index: number) => {
    setCurrentSlideIndex(index);
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
    arrow: false,
    pauseOnHover: true,
  };

  /** 2023/05/17 피드백 목록 get 요청 - by leekoby */
  const { data } = useFetchFeedbackBoardList({
    selected: "",
    selectedFeedback: "",
    sorted: "인기순",
    page: 1,
    size: 10,
  });

  return (
    <div className="h-auto">
      <div className="flex justify-between h-full">
        <h1 className="font-bold text-xl">지금 HOT🔥한 피드백</h1>
        <Link href="/feedback">
          <button className="text-3xl focus:outline-none">+</button>
        </Link>
      </div>
      {data?.pages[0].data.length === 0 ? (
        <NotSearch />
      ) : (
        <div className="h-[580px]">
          <Slider {...settings} className="h-full">
            {data?.pages.map((page) =>
              page.data.map((innerData) => {
                return (
                  <SlideWrapper className="h-full" key={innerData.feedbackBoardId}>
                    <div className="h-full mx-3">
                      <FeedbackContentItem props={innerData} position="main" />
                    </div>
                  </SlideWrapper>
                );
              }),
            )}
          </Slider>
        </div>
      )}
    </div>
  );
};

export default FeedbackPostsSlide;
