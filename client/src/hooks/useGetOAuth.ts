"use client";
import axios from "axios";
const baseUrl = process.env.NEXT_PUBLIC_BASE_URL;
import { Member } from "@/types/api";

type body = { access_token: string | null; refresh_token: string | null };
type propsBind = {
  setAccessToken: React.Dispatch<string>;
  setRefreshToken: React.Dispatch<string>;
  setMember: (member: Member | null) => void;
  router: any;
};

interface getOAuth {
  (body: body, propsBind: propsBind): Promise<void>;
}

const useGetOAuth: getOAuth = async (body, propsBind) => {
  const { access_token, refresh_token } = body;
  const { setAccessToken, setRefreshToken, setMember, router } = propsBind;

  const response = await axios.get(
    `${baseUrl}/api/login/oauth?access_token=${access_token}&refresh_token=${refresh_token}`,
  );

  const newMember = response.data;
  if (access_token) {
    setAccessToken(access_token);
    localStorage.setItem("accessToken", access_token);
  }
  if (refresh_token) {
    setRefreshToken(refresh_token);
    localStorage.setItem("refreshToken", refresh_token);
  }

  setMember(newMember);
  localStorage.setItem("member", JSON.stringify(newMember));

  router.push("/");
};

export default useGetOAuth;
