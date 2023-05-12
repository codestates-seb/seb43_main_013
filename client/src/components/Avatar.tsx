import Image from "next/image";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  src: string;
  alt?: string;
  className: string;
}

/** 2023/05/11 - 아바타 컴포넌트 - by 1-blue */
const Avatar: React.FC<Props> = ({ src, alt, className }) => (
  <figure className={twMerge("relative w-16 h-16 object-fill", className)}>
    <Image src={src} alt={alt || "이미지"} fill className="rounded-full" />
  </figure>
);

export default Avatar;
