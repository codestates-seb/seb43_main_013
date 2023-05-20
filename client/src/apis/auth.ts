import { serverInstance } from ".";

// type
import type {
  ApiLogInHandler,
  ApiLogInResponse,
  ApiLogOutHandler,
  ApiLogOutResponse,
  ApiSignUpHandler,
  ApiSignUpResponse,
} from "@/types/api";

/** 2023/05/18 - 로그아웃 요청 - by 1-blue */
export const apiLogOut: ApiLogOutHandler = async ({}) => {
  const { data } = await serverInstance.post<ApiLogOutResponse>(`/logout`);

  return data;
};

/** 2023/05/20 - 로그인 요청 - by 1-blue */
export const apiLogIn: ApiLogInHandler = async (body) => {
  const response = await serverInstance.post<ApiLogInResponse>(`/login`, body);
  const { authorization, refreshtoken } = response.headers;

  return { ...response.data, authorization, refreshtoken };
};

/** 2023/05/20 - 회원가입 요청 - by 1-blue */
export const apiSignUp: ApiSignUpHandler = async (body) => {
  const { data } = await serverInstance.post<ApiSignUpResponse>(`/signup`, body);

  return data;
};
