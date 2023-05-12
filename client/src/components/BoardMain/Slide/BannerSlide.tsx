"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Image from "next/image";
import sampleImg from "/src/public/images/sample_main.jpg";

const BannerSlide = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className="z-30 w-full border border-red-600 shadow-xl bg-main-100 ">
      <Slider {...settings} className="">
        <Image src={sampleImg} alt="" placeholder="blur" className="max-h-64" />
        <Image src={sampleImg} alt="" placeholder="blur" className="max-h-64" />
        <Image src={sampleImg} alt="" placeholder="blur" className="max-h-64" />
      </Slider>
    </div>
  );
};

export default BannerSlide;
