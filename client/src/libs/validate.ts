/** 2023/05/08 - url 검증 ( http X ) - by 1-blue */
export const validateYoutubeURL = (url: string) =>
  /(http:|https:)?(\/\/)?(www\.)?(youtube.com|youtu.be)\/(watch|embed)(\?v=|\/)?(\S+)?/.test(url);
