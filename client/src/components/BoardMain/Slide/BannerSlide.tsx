"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Image from "next/image";
import sampleImg from "/src/public/images/sample_main.jpg";
const mainBannerImage = [sampleImg, sampleImg, sampleImg];
const BannerSlide = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    fade: true,
    autoplay: true,
    autoplaySpeed: 3000,
    cssEase: "linear",
  };

  return (
    <div className="p-1 z-30 w-full border border-red-600 shadow-xl bg-main-100 ">
      <Slider {...settings} className="p-1  border border-red-600">
        {mainBannerImage &&
          mainBannerImage.map((sampleImg, index) => (
            <Image src={sampleImg} alt="" placeholder="blur" className="max-h-64" key={index} />
          ))}
      </Slider>
    </div>
  );
};

export default BannerSlide;
