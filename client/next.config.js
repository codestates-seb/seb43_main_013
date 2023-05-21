/** @type {import('next').NextConfig} */
const nextConfig = {
  experimental: {
    appDir: true,
  },

  images: {
    domains: [
      // FIXME: faker의 이미지를 사용하기 위해 추가
      "cloudflare-ipfs.com",
      // 유튜브 썸네일 링크
      "img.youtube.com",

      // default avatar image
      "ibb.co",
      // 카카오
      "k.kakaocdn.net",
      // 구글
      "lh3.googleusercontent.com",
      // 네이버
      "ssl.pstatic.net",
    ],
  },
};

// https://img.youtube.com/vi/v=Lv8CXRY3ViI&list=RDLv8CXRY3ViI/0.jpg

module.exports = nextConfig;
