import type { Metadata } from "next";

/** 2023/05/25 - 메타 데이터 기본 형태 - by 1-blue */
export const defaultMetadata: Metadata = {
  generator: "Next.js",
  applicationName: "Creator Connect",
  referrer: "origin-when-cross-origin",
  colorScheme: "dark",
  authors: [{ name: "Creator Connect", url: "https://github.com/codestates-seb/seb43_main_013" }],
  creator: "Creator Connect",
  keywords: ["Creator Connect", "초보", "유튜버", "초보 유튜버", "커뮤니티", "초보 유튜버 커뮤니티"],
  openGraph: {
    title: "Creator Connect",
    description: "초보 유튜버를 위한 커뮤니티 사이트입니다.",
    url: "https://www.hard-coding.com",
    siteName: "Creator Connect",
    images: ["/logo/logo.png"],
    locale: "ko-KR",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    title: "Creator Connect",
    description: "초보 유튜버를 위한 커뮤니티 사이트입니다.",
    creator: "Creator Connect",
    images: ["/logo/logo.png"],
  },
};

// type
interface GetMetadataHandler {
  ({ title, description, images }: { title: string; description?: string; images?: string[] }): Metadata;
}

/** 2023/05/25 - 메타데이터 제조기 - by 1-blue */
export const getMetadata: GetMetadataHandler = ({ title, description, images }) => ({
  ...defaultMetadata,
  title: "CC | " + title,
  description: "초보 유튜버를 위한 커뮤니티 사이트입니다." + "\n" + description,

  // og
  openGraph: {
    ...defaultMetadata.openGraph,
    title: "Creator Connect | " + title,
    description: "초보 유튜버를 위한 커뮤니티 사이트입니다." + "\n" + description,
    images: images ? images : ["/logo/logo.png"],
  },

  // twitter
  twitter: {
    ...defaultMetadata.twitter,
    title: "Creator Connect | " + title,
    description: "초보 유튜버를 위한 커뮤니티 사이트입니다." + "\n" + description,
    images: images ? images : ["/logo/logo.png"],
  },
});
