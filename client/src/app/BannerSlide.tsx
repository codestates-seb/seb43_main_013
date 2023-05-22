"use client";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Image from "next/image";
import sampleImg1 from "/src/public/images/sample_main1.png";
import sampleImg2 from "/src/public/images/sample_main2.png";
import sampleImg3 from "/src/public/images/sample_main3.png";
const mainBannerImage = [sampleImg1, sampleImg2, sampleImg3];
const BannerSlide = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    fade: true,
    autoplay: true,
    autoplaySpeed: 5000,
    cssEase: "linear",
    nextArrow: <></>,
    prevArrow: <></>,
  };

  return (
    <div className="p-1 z-30 w-full max-h-[360px] shadow-xl bg-sub-100 ">
      <Slider {...settings} className="p-1  ">
        {mainBannerImage &&
          mainBannerImage.map((sampleImg, index) => (
            <Image src={sampleImg} alt="" placeholder="blur" className=" h-full" key={index} />
          ))}
      </Slider>
    </div>
  );
};

export default BannerSlide;
