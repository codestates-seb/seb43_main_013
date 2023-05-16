import { useRef, useState } from "react";
import Image from "next/image";
import { PhotoIcon, ArrowPathIcon } from "@heroicons/react/24/solid";
import { twMerge } from "tailwind-merge";

// type
interface Props {
  setThumbnail: React.Dispatch<React.SetStateAction<FileList | null>>;
  className?: string;
}

/** 2023/05/15 - Photo component - by 1-blue */
const Photo: React.FC<Props> = ({ className, setThumbnail }) => {
  /** 2023/05/15 - 썸네일 ref - by 1-blue */
  const ThumbnailRef = useRef<HTMLInputElement>(null);

  /** 2023/05/15 - preview - by 1-blue */
  const [preview, setPreview] = useState("");

  /** 2023/05/15 - preview 등록 - by 1-blue */
  const onUploadPreview: React.ChangeEventHandler<HTMLInputElement> = (e) => {
    setThumbnail(e.target.files);

    // 이미 프리뷰가 있다면 제거 ( GC에게 명령 )
    if (preview) URL.revokeObjectURL(preview);

    // 썸네일이 입력되면 브라우저에서만 보여줄 수 있도록 blob url 얻기
    if (e.target.files && e.target.files.length > 0) {
      setPreview(URL.createObjectURL(e.target.files[0]));
    }
  };

  return (
    <div className={twMerge("md:w-[400px] flex flex-col", className)}>
      <label>
        <span className="text-base font-bold text-gray-800 mb-1">썸네일</span>
      </label>
      <figure className="group pt-[60%] md:pt-0 flex-1 relative border-2 border-dotted border-black rounded-md p-2">
        <input type="file" hidden ref={ThumbnailRef} onChange={onUploadPreview} />

        <button
          type="button"
          className="absolute inset-1/2 w-[calc(100%-0.5rem)] h-[calc(100%-0.5rem)] -translate-x-1/2 -translate-y-1/2 flex justify-center items-center bg-transparent rounded-md transition-colors group-hover:z-[1] group-hover:bg-black/70"
          onClick={() => ThumbnailRef.current?.click()}
        >
          {preview ? (
            <ArrowPathIcon className="w-12 h-12 text-gray-300 z-[1] transition-colors group-hover:text-gray-200" />
          ) : (
            <PhotoIcon className="w-12 h-12 text-gray-400 z-[1] transition-colors group-hover:text-gray-200" />
          )}
        </button>

        {preview && (
          <Image
            src={preview}
            alt="업로드한 썸네일"
            fill
            quality={75}
            placeholder="blur"
            blurDataURL={"blurDataURL"}
            className="p-1"
          />
        )}
      </figure>
    </div>
  );
};

export default Photo;
