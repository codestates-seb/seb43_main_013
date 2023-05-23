"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Link from "next/link";

import { ReactNode, useEffect, useState } from "react";
import SlideWrapper from "../components/BoardMain/Slide/SlideWrapper";
import { useFetchFreeBoardList } from "@/hooks/query/useFetchFreeBoardList";
import ContentItem from "@/app/free/ContentItem";
import NotSearch from "@/components/Svg/NotSearch";

const FreePostsSlide = () => {
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
    infinite: true,
    draggable: true,
    swipeToSlide: true,
    slidesToShow: silderPage,
    autoplay: true,
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

  return (
    <div>
      <div className="flex justify-between">
        <h1 className="font-bold text-xl">지금 HOT🔥한 게시글</h1>
        <Link href="/free">
          <button className="text-3xl focus:outline-none">+</button>
        </Link>
      </div>

      {data?.pages[0].data.length === 0 ? (
        <NotSearch />
      ) : (
        <div className="">
          <Slider {...settings}>
            {data?.pages.map((page) =>
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
      )}
    </div>
  );
};

export default FreePostsSlide;
