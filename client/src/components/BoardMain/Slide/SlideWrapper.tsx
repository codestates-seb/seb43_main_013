"use client";
import React, { useState, MouseEvent } from "react";

interface SlideWrapperProps extends React.HTMLAttributes<HTMLDivElement> {
  children?: React.ReactNode;
  onClick?: (event: MouseEvent<HTMLDivElement>) => void;
}

/** 2023/05/17 슬라이드 드래그 클릭 이벤트 구분하게 하는 컴포넌트 - by leekoby */
const SlideWrapper: React.FC<SlideWrapperProps> = ({ children, ...props }) => {
  const [dragging, setDragging] = useState(false);

  const handleMouseDown = () => {
    setDragging(false);
  };

  const handleMouseMove = () => {
    setDragging(true);
  };

  const handleClick = (event: MouseEvent<HTMLDivElement>) => {
    if (dragging) {
      event.stopPropagation();
      event.preventDefault();
    } else {
      if (props.onClick) {
        props.onClick(event);
      }
    }
  };
  return (
    <div {...props} onMouseDown={handleMouseDown} onMouseMove={handleMouseMove} onClickCapture={handleClick}>
      {children}
    </div>
  );
};

export default SlideWrapper;
