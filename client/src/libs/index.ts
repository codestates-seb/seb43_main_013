/** 2023/05/09 - 테스트에서 사용할 타이머 - by 1-blue */
export const timer = (time: number) => new Promise((resolve) => setTimeout(() => resolve(1), time));

/** 2023/05/12 - 유튜브 썸네일 링크 추출 - by 1-blue */
export const getYoutubeThumbnail = (url: string) => {
  // ( 썸네일 URL 얻기 -> https://abcdqbbq.tistory.com/98 )
  // 예시  -> https://www.youtube.com/watch?v=zvZ3Xw2eXC0&list=RDTrGxjB85sa4&index=3
  // 1. 영상 id 얻기 ( "?v=" 이후부터 "&" 이전 혹은 끝 값 얻기 )
  const sp = url.indexOf("?v=");
  const ep = url.includes("&") ? url.indexOf("&") : url.length;
  const vedioId = url.slice(sp + 3, ep);

  // 2. 썸네일 링크 생성
  return `https://img.youtube.com/vi/${vedioId}/0.jpg`;
};

/** 2023/05/23 - 유튜브 Iframe 링크 추출 - by 1-blue */
export const getYoutubeIframe = (url: string) => {
  // ( 썸네일 URL 얻기 -> https://abcdqbbq.tistory.com/98 )
  // 예시  -> https://www.youtube.com/watch?v=zvZ3Xw2eXC0&list=RDTrGxjB85sa4&index=3
  // 1. 영상 id 얻기 ( "?v=" 이후부터 "&" 이전 혹은 끝 값 얻기 )
  const sp = url.indexOf("?v=");
  const ep = url.includes("&") ? url.indexOf("&") : url.length;
  const vedioId = url.slice(sp + 3, ep);

  // 2. 썸네일 링크 생성
  return `https://www.youtube.com/embed/${vedioId}`;
};

export * from "./validate";
