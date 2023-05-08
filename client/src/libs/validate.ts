/** 2023/05/08 - url 검증 ( http X ) - by 1-blue */
export const validateURL = (url: string) =>
  /https:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#()?&//=]*)/.test(url);
