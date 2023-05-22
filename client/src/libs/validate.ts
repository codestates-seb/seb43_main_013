/** 2023/05/08 - youtube url 검증 ( http X ) - by 1-blue */
export const youtubeURL = (url: string) =>
  /(http:|https:)?(\/\/)?(www\.)?(youtube.com|youtu.be)\/(watch|embed)?(\?v=|\/)?(\S+)?/.test(url);

/** 2023/05/20 - phone 검증 - by 1-blue */
export const phone = (phone: string) => /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/.test(phone);

/** 2023/05/20 - email 검증 ( http X ) - by 1-blue */
export const email = (email: string) => /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/.test(email);

const validate = {
  youtubeURL,
  phone,
  email,
};

export default validate;
