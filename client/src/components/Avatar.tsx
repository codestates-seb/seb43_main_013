import Image from "next/image";
import Link from "next/link";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  src: string;
  alt?: string;
  className: string;
  href?: string;
}

/** 2023/05/11 - 아바타 컴포넌트 - by 1-blue */
const Avatar: React.FC<Props> = ({ src, alt, className, href }) =>
  href ? (
    <Link href={href}>
      <figure
        className={twMerge("relative w-16 h-16 object-fill rounded-full transition-colors hover:shadow-xl", className)}
      >
        <Image src={src} alt={alt || "이미지"} fill className="rounded-full" />
      </figure>
    </Link>
  ) : (
    <figure className={twMerge("relative w-16 h-16 object-fill rounded-full", className)}>
      <Image src={src} alt={alt || "이미지"} fill className="rounded-full" />
    </figure>
  );

export default Avatar;
